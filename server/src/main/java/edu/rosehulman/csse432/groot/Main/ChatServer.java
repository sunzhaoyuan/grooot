package edu.rosehulman.csse432.groot.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private boolean started = false;
	private List<ChatThread> chatThreads = new ArrayList<ChatThread>();

	public static void main(String[] args) {
		System.out.println("Waiting for connection!");
		new ChatServer().startServer();
	}

	private void startServer() {
		try {

			ServerSocket serverSocket = new ServerSocket(5000);
			started = true;
			while (started) {

				Socket socket = serverSocket.accept();
				System.out.println("one client connected!");

				ChatThread chatThread = new ChatThread(socket);
				chatThreads.add(chatThread);
				new Thread(chatThread).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class ChatThread implements Runnable {
		private Socket socket;
		private DataInputStream dataInputStream = null;
		private DataOutputStream dataOutputStream = null;
		private boolean isConnected = false;
		OutputStream outputStream = null;
		PrintWriter printWriter = null;

		public ChatThread(Socket socket) {
			super();
			this.socket = socket;
		}

		private void send(String strMsgIn) {
			try {
				dataOutputStream.writeUTF(strMsgIn);
				dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());

				isConnected = true;
				while (isConnected) {

					String strMsgIn = dataInputStream.readUTF();

					System.out.println(strMsgIn);

					for (int i = 0; i < chatThreads.size(); i++) {
						chatThreads.get(i).send(strMsgIn);
						dataOutputStream.writeUTF(strMsgIn);
					}
				}
			} catch (IOException e) {
				try {
					socket.close();
					chatThreads.remove(this);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					dataInputStream.close();
					dataOutputStream.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}

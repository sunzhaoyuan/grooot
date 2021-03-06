package edu.rosehulman.csse432.groot.service;

import edu.rosehulman.csse432.groot.method.Create;
import edu.rosehulman.csse432.groot.method.Get;
import edu.rosehulman.csse432.groot.method.Message;
import edu.rosehulman.csse432.groot.util.IOUtil;

import java.io.*;
import java.net.Socket;

public class ClientService implements Runnable {
	private final String QUIT_STRING = "quit";

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	public ClientService(Socket socket) {
		this.socket = socket;
	}

	private void init() {
		try {
			in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		init();
		System.out.println("Server: Handshaked with client.");
		String line = "";
		while (!line.equals("quit")) {
			try {
				line = in.readUTF();
				parseRes(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A line should contains [method] [method-body...] [backslash - as
	 * terminator]
	 * <p>
	 * Get [Chatroom] [client id] - Get the first Chatroom in the queue, return
	 * RoomName
	 * <p>
	 * Create [User | Chatroom] [options] Create User [id] - Save a new User in
	 * the table with the id provided, return status code Create Chatroom
	 * [creator id] [roome name] - Save a new Chatroom in the table, return
	 * status code
	 * <p>
	 * Message [chatroom name] [sender] [text] [text length] - Save a new
	 * message in the table, return status code
	 *
	 * @param line
	 *            the message that client send to server
	 */
	private void parseRes(String line) throws IOException {
		// TODO: handle client responds
		System.out.println("Client: " + line);
		String[] elements = line.trim().split(" ");
		assert elements.length > 1 : "Elements length not correct.";

		String method = elements[0];
		switch (method) {
		case "Get": {
			get(elements);
			break;
		}
		case "Message":
			message(elements, line);
			break;
		case "Create": {
			create(elements);
			break;
		}
		default:
			System.err.printf("[%s] method not support.", method);
			break;
		}
	}

	private void get(String[] elements) throws IOException {
		if (elements.length < 2) {
			IOUtil.sendData(out, "406", "Get method: Element length is not correct");
			return;
		}
		String determinator = elements[1];
		switch (determinator) {
		case "Chatroom":
			if (elements.length != 3) {
				IOUtil.sendData(out, "406", "Get method: Element length is not correct");
				return;
			}
			Get.getChatroomAndSendCreator(out, elements[2]);
			break;
		default:
			System.err.printf("Bad Request: [%s] in Get method not available.", determinator);
			IOUtil.sendData(out, "400",
					String.format("Bad Request. [%s] in \"Get\" method not available.", determinator));
		}
	}

	private void message(String[] elements, String line) throws IOException {
		if (elements.length < 4) {
			IOUtil.sendData(out, "406", "Message method: Element length is not correct");
			return;
		}
		StringBuilder messageBody = new StringBuilder();
		for (int i = 3; i < elements.length; i++) {
			messageBody.append(elements[i]).append(" ");
		}
		Message.sendMessage(out, elements[1], elements[2], messageBody.toString());

	}

	private void create(String[] elements) {
		String determinator = elements[1];
		switch (determinator) {
		case "User":
			if (elements.length != 3) {
				IOUtil.sendData(out, "406", "Create method: Element length is not correct");
			}
			Create.createUser(out, elements[2]);
			break;
		case "Chatroom":
			if (elements.length != 4) {
				IOUtil.sendData(out, "406", "Create method: Element length is not correct");
			}

			Create.createChatroom(out, elements[2], elements[3]);
			break;
		default:
			System.err.printf("400 Bad Request\n[%s] in Get method not available.", determinator);
			IOUtil.sendData(out, "400",
					String.format("Bad Request. [%s] in \"Get\" method not available.", determinator));
		}
	}
}

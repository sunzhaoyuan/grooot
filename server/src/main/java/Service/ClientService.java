package Service;

import java.io.*;
import java.net.Socket;

public class ClientService implements Runnable{
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
        try {
            String input = in.readUTF();
            while (!input.equals("HELLO")) {
                input = in.readUTF();
            }
            out.writeUTF("HELLO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        System.out.println("Server: Handshake with client.");
        String line = "";
        while (!line.equals("QUIT")) {
            // TODO:
            try {
                line = in.readUTF();
                parseRes(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseRes(String line) {
        // TODO: handle client responds
    }
}

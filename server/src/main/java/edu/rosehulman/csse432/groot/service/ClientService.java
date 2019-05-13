package edu.rosehulman.csse432.groot.service;

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
        // Get rid of it, useless implementation
//        try {
//            String input = in.readUTF();
//            while (!input.equals("HELLO")) {
//
//                input = in.readUTF();
//            }
//            out.writeUTF("HELLO");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
     * A line should contains [method] [method-body...] [backslash - as terminator]
     * <p>
     * Get [Chatroom] - Get the first Chatroom in the queue, return HostName
     * <p>
     * Create [User | Chatroom] [options]
     * Create User [id]   - Save a new User in the table with the id provided, return status code
     * Create Chatroom [creator id] [roome name]   - Save a new Chatroom in the table, return status code
     * <p>
     * Message [chatroom name] [sender] [text] - Save a new message in the table, return status code
     *
     * @param line the message that client send to server
     */
    private void parseRes(String line) {
        // TODO: handle client responds
        System.out.println("Client: " + line);
        String[] elements = line.trim().split(" ");
        assert elements.length > 1 : "Elements length not correct.";

        String method = elements[0];
        if (method.equals("Get")) {
            String determinator = elements[1];
            switch (determinator) {
                case "Characters":
                    assert edu.rosehulman.csse432.groot.method.Get.getAndSendCharacters(out) : "getAndSendCharacters returns false";
                    break;
                case "Chatrooms":
                    assert edu.rosehulman.csse432.groot.method.Get.getChatroomAndSendCrator(out) : "getChatroomAndSendCrator returns false";
                    break;
                default:
                    System.err.printf("Bad Request: [%s] in Get method not available.", determinator);
            }
        } else if (method.equals("Message")) {
            assert elements.length == 4 : "Message method: Element length is not correct";
            assert edu.rosehulman.csse432.groot.method.Message.sendMessage(out, elements[1], elements[2], elements[3]);
        } else {
            System.err.printf("[%s] method not support.", method);
        }
    }
}

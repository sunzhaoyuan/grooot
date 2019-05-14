package edu.rosehulman.csse432.groot.service;

import edu.rosehulman.csse432.groot.method.Create;
import edu.rosehulman.csse432.groot.method.Get;
import edu.rosehulman.csse432.groot.method.Message;

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
        // Deprecated, useless implementation
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
     * Get [Chatroom] [client id] - Get the first Chatroom in the queue, return HostName
     * <p>
     * Create [User | Chatroom] [options]
     * Create User [id]   - Save a new User in the table with the id provided, return status code
     * Create Chatroom [creator id] [roome name]   - Save a new Chatroom in the table, return status code
     * <p>
     * Message [chatroom name] [sender] [text] - Save a new message in the table, return status code
     *
     * @param line the message that client send to server
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
                message(elements);
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
            out.writeUTF("406 Not Acceptable\nGet method: Element length is not correct");
            return;
        }
        String determinator = elements[1];
        switch (determinator) {
            case "Chatrooms":
                if (elements.length != 3) {
                    out.writeUTF("406 Not Acceptable\nGet method: Element length is not correct");
                    return;
                }
                assert Get.getChatroomAndSendCreator(out, elements[2]) : "getChatroomAndSendCreator returns false";
                break;
            default:
                System.err.printf("Bad Request: [%s] in Get method not available.", determinator);
                out.writeUTF(String.format("400 Bad Request.\n[%s] in Get method not available.", determinator));
        }
    }

    private void message(String[] elements) throws IOException {
        if (elements.length != 4) {
            out.writeUTF("406 Not Acceptable\nMessage method: Element length is not correct");
            return;
        }
        assert Message.sendMessage(out, elements[1], elements[2], elements[3]);
    }

    private void create(String[] elements) throws IOException {
        String determinator = elements[1];
        switch (determinator) {
            case "User":
                if (elements.length != 3) {
                    out.writeUTF("406 Not Acceptable\nCreate method: Element length is not correct");
                }
                assert Create.CreateUser(elements[2]);
                break;
            case "Chatroom":
                if (elements.length != 4) {
                    out.writeUTF("406 Not Acceptable\nCreate method: Element length is not correct");
                }
                assert Create.CreateChatroom(elements[2], elements[3]);
                break;
            default:
                System.err.printf("400 Bad Request\n[%s] in Get method not available.", determinator);
                out.writeUTF(String.format("Bad Request: [%s] in Get method not available.", determinator));
        }
    }
}

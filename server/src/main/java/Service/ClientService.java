package Service;

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
     * Get [Characters | Chatrooms | Users] [option]
     * - Characters 			: Get a list of all characters
     * - Chatrooms [chatroom id]	: Get a list of all chatrooms or by chatroom id
     * - Users [user id] 			: Get a list of all users or by userid
     * <p>
     * Message [from user] [to chatroom] [message body]
     *
     * @param line
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
                    assert Method.Get.getAndSendCharacters(out) : "getAndSendCharacters returns false";
                    break;
                case "Chatrooms":
                    assert Method.Get.getAndSendChatrooms(out) : "getAndSendChatrooms returns false";
                    break;
                default:
                    System.err.printf("Bad Request: [%s] in Get method not available.", determinator);
            }
        } else if (method.equals("Message")) {
            assert elements.length == 4 : "Message method: Element length is not correct";
            assert Method.Message.sendMessage(out, elements[1], elements[2], elements[3]);
        } else {
            System.err.printf("[%s] method not support.", method);
        }
    }
}

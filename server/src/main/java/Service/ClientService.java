package Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientService implements Runnable{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientService(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(this.socket.getInputStream());
            out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line = "";
        while (!line.equals("quit")) {
            // TODO:
        }
    }
}

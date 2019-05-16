package edu.rosehulman.csse432.groot.main;

import edu.rosehulman.csse432.groot.service.ClientService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class Server {
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;

    private FirebaseDatabase database;

    private boolean isClosed;
    private Thread[] threads;

    /**
     * A safe way to start the server. Notify outside world (Launcher) that the server is ready.
     *
     * @return
     */
    public static CompletableFuture<Server> startServer() {
        return CompletableFuture.supplyAsync(
                () -> {
                    Server server = null;
                    try {
                        server = new Server();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        exit(1);
                    }
                    return server;
                }
        );
    }

    private Server() throws ExecutionException, InterruptedException {
        init();
        System.out.println("Server: Initialization done.");

        this.threads[0] = new Thread(
                new ServerService(this.isClosed)
        );
        this.threads[0].start();
    }

    private void init() throws ExecutionException, InterruptedException {
        this.threads = new Thread[1];
        this.isClosed = false;

        CompletableFuture<Void> initFuture = CompletableFuture.runAsync(
                () -> {
                    // start db connection
                    try {
                        startDatabaseConn();
                    } catch (IOException e) {
                        e.printStackTrace();
                        exit(1);
                    }
                    System.out.println("Server: Database connection established");
                })
                .thenRun(
                        () -> {
                            // start socket and listen client on port
                            try {
                                serverSocket = new ServerSocket(Configuration.getInstance().getSERVER_PORT());
                            } catch (IOException e) {
                                e.printStackTrace();
                                exit(1);
                            }
                            System.out.println("Server: Socket opened.");
                        }
                );
        initFuture.get();
    }

    /**
     * Handle database connection.
     */
    private void startDatabaseConn() throws IOException {
        // initialize database connection
        FileInputStream serviceAccount = new FileInputStream(Configuration.getInstance().getSECRET_LOCATION());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(Configuration.getInstance().getDB_URL())
                .build();
        FirebaseApp.initializeApp(options);
    }

    private class ServerService implements Runnable {

        private boolean isClosed;

        ServerService(boolean isClosed) {
            this.isClosed = isClosed;
        }

        /**
         * Keep listening client connections. Once a connection is established, create and run a new ClientService.
         */
        @Override
        public void run() {
            while (!isClosed) {
                System.out.println("Server: Waiting for client connections...");
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Server: Client accepted");
                new Thread(new ClientService(clientSocket)).start();
            }
        }
    }

    /**
     * Close the server completely
     *
     * @return
     */
    public CompletableFuture close() {
        return CompletableFuture.runAsync(
                () -> {
                    // TODO: handle close
                    try {
                        serverSocket.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}

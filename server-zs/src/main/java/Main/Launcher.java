package Main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The launcher of the server.
 */
public class Launcher {
    public static void main(String[] args) {
        Thread mainThread = new Thread(
                () -> {
                    CompletableFuture<Server> serverFuture = Server.startServer();
                    try {
                        serverFuture.get();
                        System.out.printf("Server: Server is listening on port %s\n", Configuration.getInstance().getSERVER_PORT());
                        // do other things here
                        while (true)
                            ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                , "main");
        mainThread.start();
    }
}

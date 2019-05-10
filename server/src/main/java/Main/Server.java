package Main;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class Server {

	private ServerSocket serverSocket;
	private DataInputStream dataInputStream;
	private Socket socket;

	private FirebaseDatabase database;

	private boolean isClosed;
	private Thread[] threads;

	/**
	 * A safe way to start the server. Notify outside world (Launcher) that the
	 * server is ready.
	 *
	 * @return
	 */
//	public Server(Socket socket) {
//		this.socket = socket;
//	}

	public static CompletableFuture<Server> startServer() {
		CompletableFuture<Server> startFuture = CompletableFuture.supplyAsync(() -> {
			Server server = null;
			try {
				server = new Server();
			} catch (ExecutionException e) {
				e.printStackTrace();
				exit(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				exit(1);
			}
			return server;
		});
		return startFuture;
	}

	private Server() throws ExecutionException, InterruptedException {
		init();
		System.out.println("Server: Initialization done.");

		this.threads[0] = new Thread(new ServerService(this.isClosed));
		this.threads[0].start();
	}

	private void init() throws ExecutionException, InterruptedException {
		this.threads = new Thread[1];
		this.isClosed = false;

		CompletableFuture<Void> initFuture = CompletableFuture.runAsync(() -> {
			// start db connection
			try {
				startDatabaseConn();
			} catch (IOException e) {
				e.printStackTrace();
				exit(1);
			}
			System.out.println("Server: Database connection established");
		}).thenRun(() -> {
			// serverSocket = new
			// ServerSocket(Configuration.getInstance().getSERVER_PORT());
			ServerService serverConn = new ServerService(true);
			serverConn.run();
			System.out.println("Server: Socket opened.");
		});
		initFuture.get();
	}

	/**
	 * Handle database connection.
	 */
	private void startDatabaseConn() throws IOException {
		// TODO: handle database connection
		// TODO: https://github.com/bane73/firebase4j
		// FirebaseOptions options = new FirebaseOptions.Builder()
		// .setCredentials(GoogleCredentials.getApplicationDefault())
		// .setDatabaseUrl(Configuration.getInstance().getDB_URL()).build();
		// FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
		// System.out.printf("Server: Firebase app name %s\n",
		// firebaseApp.getName());
		//
		// // TODO: double check this implementation
		// this.database = FirebaseDatabase.getInstance(firebaseApp);
	}

	private class ServerService implements Runnable {

		private boolean isClosed;

		public ServerService(boolean isClosed) {
			this.isClosed = isClosed;
		}

		@Override
		public void run() {
			while (!isClosed) {

				try {
					ServerSocket server = new ServerSocket(5000);
					// Socket socket = server.accept();
					try {
						DataInputStream in = new DataInputStream(socket.getInputStream());
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						// Scanner scanner = new Scanner(System.in);
						while (true) {
							while (in.available() > 0) {
								String accept = in.readUTF();
								System.out.println(accept);
								if (accept.equals("quit")) {
									socket.close();
									server.close();
									System.exit(0);
								}
								// String send = scanner.nextLine();
								// System.out.println("server: " + send);
								out.writeUTF("fuck you");
							}
						}
					} finally {
						socket.close();
						server.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * Close the server completely
	 *
	 * @return
	 */

	private void GetMessageFromClient() {
		try {

			int length = dataInputStream.read();

			byte[] body = new byte[length];
			dataInputStream.read(body);

			String message = new String(body);
			System.out.println("client" + message);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public CompletableFuture close() {
		CompletableFuture<Void> closeFuture = CompletableFuture.runAsync(() -> {
			// TODO: handle close
		});
		return closeFuture;
	}
}

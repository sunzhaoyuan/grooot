import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateMessage {
	public static long maxTimeStamp = -1;

	public static void update(String currentRoomName, JTextArea textArea) throws IOException {
		FileInputStream serviceAccount = new FileInputStream("/Users/liuz6/Downloads/groot-new-secret.json");
		FirebaseOptions options = null;
		options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://groot-2e98b.firebaseio.com/").build();
		FirebaseApp.initializeApp(options);

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

		ref.child("Messages").orderByChild("timeStamp").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				snapshot.getChildren().forEach((dataSnapshot) -> {
					Message message = dataSnapshot.getValue(Message.class);
					if (message.chatRoom.equals(currentRoomName)) {
						// TODO: add to panel
						// TODO: other TODO, send message to server
						if (message.timeStamp.compareTo(maxTimeStamp) > 0) {
							textArea.append(message.sender + ": " + message.text + "\n");
							maxTimeStamp = message.timeStamp;
						}
					}
				});
			}

			@Override
			public void onCancelled(DatabaseError error) {

			}
		});

	}
	//
	// public static void main(String[] args) {
	// try {
	// update("",);
	// while (true){
	//
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}

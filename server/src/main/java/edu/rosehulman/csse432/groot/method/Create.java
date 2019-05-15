package edu.rosehulman.csse432.groot.method;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import edu.rosehulman.csse432.groot.main.Configuration;
import edu.rosehulman.csse432.groot.object.ChatRoom;
import edu.rosehulman.csse432.groot.object.User;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Create {

	public static void createUser(DataOutputStream out, String userid) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
		ref.push().setValue(new User(userid), (error, ref1) -> {
			try {
				if (error != null) {
					out.writeUTF(String.format("Error: %s, Message %s", error.getCode(), error.getMessage()));
				} else {
					out.writeUTF("200");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void createChatroom(DataOutputStream out, String creatorid, String roomname) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChatRooms");
		ref.push().setValue(new ChatRoom("REPLACE_ME", creatorid, roomname, true), (error, ref1) -> {
			try {
				if (error != null) {
					out.writeUTF(String.format("Error: %s, Message %s", error.getCode(), error.getMessage()));
					out.flush();
				} else {
					out.writeUTF("200");
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void main(String[] args) throws IOException {
		FileInputStream serviceAccount = new FileInputStream(Configuration.getInstance().getSECRET_LOCATION());
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(Configuration.getInstance().getDB_URL()).build();
		FirebaseApp.initializeApp(options);

		// createUser(null, "testuser_3");
		// createChatroom(null, "testuser_3", "test_create_room_1");
		//
		// while (true)
		// ;
	}
}

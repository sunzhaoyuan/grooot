package edu.rosehulman.csse432.groot.method;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import edu.rosehulman.csse432.groot.main.Configuration;
import edu.rosehulman.csse432.groot.object.ChatRoom;
import edu.rosehulman.csse432.groot.object.User;
import edu.rosehulman.csse432.groot.util.IOUtil;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Create {
	public static void createUser(DataOutputStream out, String userid) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
		ref.push().setValue(new User(userid), (error, ref1) -> {
			if (error != null) {
				IOUtil.sendData(out, String.valueOf(error.getCode()), error.getMessage());
			} else {
				IOUtil.sendData(out, "200", "");
			}
		});
	}

	public static void createChatroom(DataOutputStream out, String creatorid, String roomname) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChatRooms");
		ref.push().setValue(new ChatRoom("REPLACE_ME", creatorid, roomname, true), (error, ref1) -> {
			if (error != null) {
				IOUtil.sendData(out, String.valueOf(error.getCode()), error.getMessage());

			} else {
				IOUtil.sendData(out, "200", "");
			}
		});
	}

	public static void main(String[] args) throws IOException {
		FileInputStream serviceAccount = new FileInputStream(Configuration.getInstance().getSECRET_LOCATION());
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(Configuration.getInstance().getDB_URL()).build();
		FirebaseApp.initializeApp(options);

	}
}

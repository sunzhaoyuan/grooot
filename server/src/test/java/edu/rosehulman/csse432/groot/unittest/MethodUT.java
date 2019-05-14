package edu.rosehulman.csse432.groot.unittest;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import edu.rosehulman.csse432.groot.main.Configuration;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Deprecated
public class MethodUT {

    @Test
    public void TestGetChatroom() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(Configuration.getInstance().getSECRET_LOCATION());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(Configuration.getInstance().getDB_URL())
                .build();
        FirebaseApp.initializeApp(options);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ChatRooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String chatroom = snapshot.toString();
                System.out.println(chatroom);
                //TODO
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}

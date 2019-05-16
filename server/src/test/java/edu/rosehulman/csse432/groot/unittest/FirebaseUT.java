package edu.rosehulman.csse432.groot.unittest;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import edu.rosehulman.csse432.groot.main.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@Deprecated
public class FirebaseUT {

    @Test
    public void TestAll() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(Configuration.getInstance().getSECRET_LOCATION());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(Configuration.getInstance().getDB_URL())
                .build();
        FirebaseApp.initializeApp(options);

        // Test Retrieve With Child
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                assertTrue(snapshot.exists());
                assertThat(snapshot.toString(), containsString("ForUnitTest"));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        // Test Retrieve With Reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                assertTrue(snapshot.exists());
                assertThat(snapshot.toString(), containsString("ForUnitTest"));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}

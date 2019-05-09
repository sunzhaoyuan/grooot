package MethodUtil;

import com.google.firebase.database.*;

import java.io.DataOutputStream;
import java.net.Socket;

public class GetUtil {

    public static boolean getAndSendCharacters(DataOutputStream out) {
        System.out.println("Retriving Characters list");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Characters").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );
        System.out.println("Send out Characters list");
        return true;
    }

    public static boolean getAndSendChatrooms(DataOutputStream out) {
        return true;
    }

}

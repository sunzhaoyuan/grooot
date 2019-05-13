package edu.rosehulman.csse432.groot.method;

import com.google.firebase.database.*;

import java.io.DataOutputStream;

public class Get {

    public static boolean getChatroomAndSendCreator(DataOutputStream out) {
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
        return true;
    }

    @Deprecated
    public static boolean getAndSendCharacters(DataOutputStream out) {
        System.out.println("Retriving Characters list");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String character = snapshot.child("Characters").toString();
                        System.out.println(character);
                        //TODO
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );
        System.out.println("Send out Characters list");
        return true;
    }

}

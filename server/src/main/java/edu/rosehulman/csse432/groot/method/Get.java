package edu.rosehulman.csse432.groot.method;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import edu.rosehulman.csse432.groot.main.Configuration;
import edu.rosehulman.csse432.groot.object.ChatRoom;
import edu.rosehulman.csse432.groot.util.IOUtil;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Get {

    public static void getChatroomAndSendCreator(DataOutputStream out, String clientname) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        // Get the first waiting chatroom, TODO: Not necessarily be the "time first"
        ref.child("ChatRooms")
                .orderByChild("waiting")
                .equalTo(true)
                .limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
//                        System.out.println(snapshot.getChildren().iterator().next().getValue(ChatRoom.class).toString());

                        // it should only contain one dataSnapshot
                        snapshot.getChildren()
                                .forEach(dataSnapshot -> {
                                    // get the chatroom object
                                    ChatRoom chatRoom = dataSnapshot.getValue(ChatRoom.class);
                                    String roomName = chatRoom.getRoomName();
                                    System.out.println(roomName);

                                    // send hostname back to client
//                                    try {
//                                        out.writeUTF(hostName);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }

                                    // update waiting to false and clientname to the real clientname
                                    chatRoom.setClientName(clientname);
                                    chatRoom.setWaiting(false);
                                    Map<String, Object> map = new HashMap<>();
                                    map.put(chatRoom.key, chatRoom);
                                    FirebaseDatabase.getInstance().getReference("ChatRooms")
                                            .updateChildren(map, (error, ref1) -> {
                                                if (error != null) {
//                                                        out.writeUTF(String.format("Error: %s, Message %s", error.getCode(), error.getMessage()));
                                                    IOUtil.sendData(out, String.valueOf(error.getCode()), error.getMessage());
                                                } else {
                                                    // send hostname back to client
//                                                        out.writeUTF(roomName);
                                                    IOUtil.sendData(out, "200", roomName);
                                                }
                                            });
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
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

    public static void main(String[] args) throws IOException {
        FileInputStream serviceAccount = new FileInputStream(Configuration.getInstance().getSECRET_LOCATION());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(Configuration.getInstance().getDB_URL())
                .build();
        FirebaseApp.initializeApp(options);

//        getChatroomAndSendCreator(null, "changed_name2");

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        Map<String, ChatRoom> chatRoomMap = new HashMap<>();
//        chatRoomMap.put("Test_chatroom2", new ChatRoom("t-c", "t-h", "Test_chatroom2", true));
//        ref.child("ChatRooms").setValueAsync(chatRoomMap);

//        chatRoom.setClientName(clientname);
//        chatRoom.setWaiting(false);
//        HashMap<String, Object> map = new HashMap<String, Object>() {{
//            put(chatRoom.getRoomName(), chatRoom);
//        }};
//        ref.child("ChatRooms").setValueAsync(map);

//        while (true)
//            ;
    }

}

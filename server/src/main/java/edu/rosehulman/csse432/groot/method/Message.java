package edu.rosehulman.csse432.groot.method;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import edu.rosehulman.csse432.groot.util.IOUtil;

import java.io.DataOutputStream;
import java.io.IOException;

public class Message {

    public static void sendMessage(DataOutputStream out, String chatRoom, String sender, String text) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Messages");
        ref.push().setValue(new edu.rosehulman.csse432.groot.object.Message(chatRoom, sender, text), (error, ref1) -> {
            if (error != null) {
//                    out.writeUTF(String.format("Error %s, Message %s", error.getCode(), error.getMessage()));
                IOUtil.sendData(out, String.valueOf(error.getCode()), error.getMessage());
            } else {
//                    out.writeUTF("200");
                IOUtil.sendData(out, "200", "");
            }
        });
    }
}

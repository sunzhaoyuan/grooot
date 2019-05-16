package edu.rosehulman.csse432.groot.util;

import com.google.firebase.database.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.IOException;

public class IOUtil {

    public static void sendData(@NotNull DataOutputStream out, @NotNull String code, String message) {
        try {
            out.writeUTF(String.format("Code: %s, Message: %s\n", code, message));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

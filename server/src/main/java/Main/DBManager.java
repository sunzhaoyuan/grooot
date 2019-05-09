//package Main;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//
//import java.io.IOException;
//
//public class DBManager {
//
//    private DBManager() {
//    }
//
//    private static DBManager instance;
//    private static FirebaseOptions options;
//    private static FirebaseApp app;
//
//    public static DBManager getInstance() {
//        if (instance == null) {
//            instance = new DBManager();
//        }
//        return instance;
//    }
//
//
//    public static FirebaseOptions setOptions(FirebaseOptions o) {
//        getInstance();
//        options = o;
//        return options;
//    }
//
//    /**
//     * Connect to the firebase and return a FirebaseOptions instance. If options has not been registered, create
//     * a default options.
//     *
//     * @return
//     * @throws IOException
//     */
//    public static FirebaseOptions getOptions() throws IOException {
//        if (options == null) {
//            getInstance();
//            options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.getApplicationDefault())
//                    .setDatabaseUrl(Configuration.getInstance().getDB_URL())
//                    .build();
//        }
//        return options;
//    }
//
//    public static FirebaseApp setApp(FirebaseApp a) {
//        getInstance();
//        app = a;
//        return app;
//    }
//
//    public static FirebaseApp getApp() throws IOException {
//        if (app == null) {
//            getInstance();
//            app = FirebaseApp.initializeApp(getOptions());
//        }
//        return app;
//    }
//}

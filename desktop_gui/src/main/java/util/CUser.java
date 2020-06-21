package util;

import client.ChatController;

import java.util.prefs.Preferences;

public abstract class CUser {

    protected static String username;
    public static final Preferences userPrefs = Preferences.userRoot();
//    public static final ChatController chatController = new ChatController("linux5.csie.ntu.edu.tw", 12345);
    public static final ChatController chatController = new ChatController("127.0.0.1", 12345);

    public static final String NOTIFICATION_PREF = "Notification";

    // User Info getters & setters
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        CUser.username = username;
    }

}

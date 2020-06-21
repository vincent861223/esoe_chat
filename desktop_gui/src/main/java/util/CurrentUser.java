package util;

import client.ChatController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.util.prefs.Preferences;

public abstract class CurrentUser {

    protected static String username;
    public static final Preferences userPrefs = Preferences.userRoot();
    public static final ChatController chatController = new ChatController("127.0.0.1", 12345);

    public static final String NOTIFICANTION_PREF = "Notification";

    // User Info getters & setters
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    // for testing
    public static void testLogin() {
        chatController.login("Vincent", "123");
        setUsername("Vincent");
    }


}

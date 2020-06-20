package util;

import client.ChatController;
import container.Response;

// This is the common parent of all controllers that should hold User information
public class CurrentUserInfo {

    static public ChatController chatController = new ChatController("127.0.0.1", 12345);

    // TODO:  preference
    //       + profile pic? --> use placeholder now
    //       + logout
    //       + notification
    static String username;

    // User Info getters & setters
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUserInfo.username = username;
    }

    // for testing
    public static void testLogin() {
        chatController.login("Vincent", "123");
        setUsername("Vincent");
    }


}

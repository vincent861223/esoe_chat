package util;

import client.ChatController;

// This is the common parent of all controllers that should hold User information
public class CurrentUser {

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
        CurrentUser.username = username;
    }

    // for testing
    public static void testLogin() {
        chatController.login("Vincent", "123");
        setUsername("Vincent");
    }


}

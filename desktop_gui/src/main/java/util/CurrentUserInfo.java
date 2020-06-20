package util;

import client.ChatController;
import container.Response;

// This is the common parent of all controllers that should hold User information
public class CurrentUserInfo {

    static public ChatController chatController = new ChatController("127.0.0.1", 12345);

    // User Info
    //    static String userID; -> save in chatcontroller
    // TODO: user friend list, chatroom list, preference
    //       + group?
    //       + self-defined name
    //       + profile pic? --> use placeholder now
    //       + Utilities: change password / sent email
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
    }


}

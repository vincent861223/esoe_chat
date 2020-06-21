package util;

import java.util.StringTokenizer;

public abstract class ChatInfo {

    static int numberOfMember;

    public static String getChatroomName(String chatroomID) {
        String myName = CurrentUser.chatController.getUsername().msg;
        String nameString = CurrentUser.chatController.getChatroomName(chatroomID).msg;
        StringBuilder ret = new StringBuilder();
        String[] names = nameString.split(" ");
        for (int i = 0; i < names.length - 1; i++) {
            if (!names[i].equals(myName)) {
                ret.append(names[i]).append(", ");
            }
        }
        ret.append(names[names.length - 1]);
        return ret.toString();
    }

    public static int getNumberOfMembers(String chatroomID) {
        String nameString = CurrentUser.chatController.getChatroomName(chatroomID).msg;
        numberOfMember = nameString.split(" ").length;
        return numberOfMember;
    }

    public static int getNumberOfMembers() {
        return numberOfMember;
    }

}

package util;

import java.util.StringTokenizer;

public abstract class ChatInfo {

    static int numberOfMember;

    public static String getChatroomName(String chatroomID) {
        String myName = CUser.chatController.getUsername().msg;
        StringBuilder ret = new StringBuilder();
        StringTokenizer str = new StringTokenizer(CUser.chatController.getChatroomName(chatroomID).msg);
        while (str.hasMoreTokens()) {
            String name = str.nextToken();
            if (!name.equals(myName)) {
                ret.append(name).append(", ");
            }
        }
        ret.delete(ret.length() - 2, ret.length() - 1);
        return ret.toString();
    }

    public static int getNumberOfMembers(String chatroomID) {
        String nameString = CUser.chatController.getChatroomName(chatroomID).msg;
        numberOfMember = nameString.split(" ").length;
        return numberOfMember;
    }

}

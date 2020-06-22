package util;

import java.util.StringTokenizer;

public abstract class ChatInfo {

    static int numberOfMember;

    /**
     * Determines display chatroom name
     * @param chatroomID
     * @return display chatroom name
     */
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

    /**
     * Calculates the number of chatroom members
     * @param chatroomID
     * @return the number of chatroom members
     */
    public static int getNumberOfMembers(String chatroomID) {
        String nameString = CUser.chatController.getChatroomName(chatroomID).msg;
        numberOfMember = nameString.split(" ").length;
        return numberOfMember;
    }

}

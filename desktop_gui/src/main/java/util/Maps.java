package util;

import container.Response;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.ChatroomController;

import java.io.IOException;
import java.util.HashMap;

public class Maps {

    public static HashMap<String, Stage> stages = new HashMap<>();
    public static HashMap<String, Parent> parents = new HashMap<>();
    public static HashMap<String, Parent> chatrooms = new HashMap<>();
    public static HashMap<String, ChatroomController> chatroomControllers = new HashMap<>();
    public static HashMap<String, Object> controllers = new HashMap<>();

    public static final String FRIEND_LIST = "friendListSlide";
    public static final String CHAT_LIST = "chatListSlide";
    public static final String ADD_FRIEND_LIST = "addFriendSlide";
    public static final String SETTING = "settingSlide";
    public static final String ROOT_STACK_PANE = "rootStackPane";
    public static final String ADD_FRIEND_DIALOG = "addFriendDialog";
    public static final String ALERT_DIALOG = "alertDialog";
    public static final String NEW_CHAT_DIALOG = "newChatDialog";

    private static Response response;
    private static BorderPane borderPane;

    public static void createNewChatroom(String[] members) throws IOException {
        response = CurrentUserInfo.chatController.creatChatroom(members);
        displayChatroom(response.getMsg());
    }

    public static void displayChatroom(String chatroomID) throws IOException {
        if (!chatrooms.containsKey(chatroomID)) {
            loadPane(chatroomID);
        }
        VBox vBox = (VBox) chatrooms.get(chatroomID);
        borderPane.setCenter(vBox);
        ChatroomController controller = chatroomControllers.get(chatroomID);
        controller.setChatroomTitle(chatroomID);
    }

    private static Parent loadPane(String chatroomID) throws IOException {
        FXMLLoader loader = new FXMLLoader(Maps.class.getResource( "chatroom.fxml"));
        Parent root = loader.load();
        chatrooms.put(chatroomID, root);
        chatroomControllers.put(chatroomID, loader.getController());
        return root;
    }

    public static void setBorderPane(BorderPane borderPane) {
        Maps.borderPane = borderPane;
    }

}

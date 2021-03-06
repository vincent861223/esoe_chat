package util;

import container.Response;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.ChatListSlideController;
import main.ChatroomController;
import main.ListCellChatroomItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Store stages, scenes, controllers
public abstract class Maps {

    public static final Map<String, Stage> stages = new HashMap<>();
    public static final Map<String, Parent> parents = new HashMap<>();
    public static final Map<String, Parent> chatrooms = new HashMap<>();
    public static final Map<String, ListCellChatroomItem> chatroomListItems = new HashMap<>();
    public static final Map<String, ChatroomController> chatroomControllers = new HashMap<>();
    public static final Map<String, Object> controllers = new HashMap<>();
    public static final Map<String, Boolean> notificationPrefs = new HashMap<>();

    // Stage Constants
    public static final String LOGIN_STAGE = "loginStage";
    public static final String MAIN_STAGE = "mainStage";

    // Slide Constants
    public static final String FRIEND_LIST = "friendListSlide";
    public static final String CHAT_LIST = "chatListSlide";
    public static final String ADD_FRIEND_LIST = "addFriendSlide";
    public static final String SETTING = "settingSlide";
    public static final String ROOT_STACK_PANE = "rootStackPane";
    public static final String ADD_FRIEND_DIALOG = "addFriendDialog";
    public static final String ALERT_DIALOG = "alertDialog";
    public static final String NEW_CHAT_DIALOG = "newChatDialog";

    private static BorderPane borderPane;

    private Maps() {}

    /**
     * creates a new chatroom
     * @param members
     * @throws IOException
     */
    public static void createNewChatroom(String[] members) throws IOException {
        Response response = CUser.chatController.creatChatroom(members);
        displayChatroom(response.getMsg());
    }

    /**
     * displays chatroom on the right pane(borderPane's center)
     * @param chatroomID
     * @throws IOException
     */
    public static void displayChatroom(String chatroomID) throws IOException {
        if (!chatrooms.containsKey(chatroomID)) {
            loadPane(chatroomID);
            ((ChatListSlideController) controllers.get(CHAT_LIST)).reload();
        }
        VBox vBox = (VBox) chatrooms.get(chatroomID);
        borderPane.setCenter(vBox);

    }

    /**
     * loads chatroom pane
     * @param chatroomID
     * @return the chatroom controller
     * @throws IOException
     */
    public static ChatroomController loadPane(String chatroomID) throws IOException {
        FXMLLoader loader = new FXMLLoader(Maps.class.getResource( "chatroom.fxml"));
        Parent root = loader.load();
        chatrooms.put(chatroomID, root);
        ChatroomController controller = loader.getController();
        controller.setChatroomID(chatroomID);
        chatroomControllers.put(chatroomID, controller);
        notificationPrefs.put(chatroomID, true);
        return controller;
    }

    /**
     * sets borderPane for displaying chatroom
     * @param borderPane
     */
    public static void setBorderPane(BorderPane borderPane) {
        Maps.borderPane = borderPane;
    }
}

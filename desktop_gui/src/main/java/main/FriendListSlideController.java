package main;

import container.Friend;
import container.FriendList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import util.CurrentUserInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendListSlideController implements Initializable, ListviewController {

    @FXML
    private VBox vBox;
    @FXML
    private ListView<ListCellItem> listView;
    private final ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();

    private static final String[] selectedFriend = { "" };
    private static final ContextMenu contextMenu = new ContextMenu();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listView.setFocusTraversable(false);
        Platform.runLater(this::reload);

        MenuItem chat = new MenuItem("Chat");
        MenuItem block = new MenuItem("Block");
        contextMenu.getItems().addAll(chat, block);

        chat.setOnAction(e -> {
            CurrentUserInfo.response =  CurrentUserInfo.chatController.creatChatroom(selectedFriend);
//                System.out.println(CurrentUserInfo.response);
            // TODO: the right slide changes to this chat
        });

        // TODO: block friend
//            block.setOnAction(e -> {
//
//            });
    }

    @Override
    public void reload() {
        obsList.clear();
        CurrentUserInfo.response = CurrentUserInfo.chatController.getFriend();
        FriendList friendList = (FriendList) CurrentUserInfo.response.info;
        for(Friend friend: friendList.friends){
            if (!friend.getPending() && !friend.getBlocked())
                obsList.add(new ListCellLabelItem(friend.getFriendUsername()));
        }


        listView.getItems().clear();
        listView.getItems().addAll(obsList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(p -> {
            ListCell<ListCellItem> cell = new ListViewCell();
            cell.setOnMousePressed(e -> {
                if (e.isSecondaryButtonDown()) {
                    selectedFriend[0] =  cell.getItem().getLabel1Text();
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
    }
}

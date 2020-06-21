package main;

import container.Friend;
import container.FriendList;
import container.Response;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import util.CurrentUser;
import util.Maps;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class FriendListSlideController implements Initializable, ListviewController {

    @FXML
    private ListView<ListCellItem> listView;
    private final ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();
    private final Set<String> removeSet = new HashSet<>();
    private final Set<String> currentSet = new HashSet<>();

    private static final String[] selectedFriend = { "" };
    private static final ContextMenu contextMenu = new ContextMenu();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(p -> {
            ListCell<ListCellItem> cell = new ListViewCell();
            cell.setOnMousePressed(e -> {
                if (e.isSecondaryButtonDown()) {
                    selectedFriend[0] =  cell.getItem().getLabelText();
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
        listView.setItems(obsList);

        Platform.runLater(this::reload);

        MenuItem chat = new MenuItem("Chat");
        MenuItem block = new MenuItem("Block");
        contextMenu.getItems().addAll(chat, block);

        chat.setOnAction(e -> {
            try {
                Maps.createNewChatroom(selectedFriend);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        // TODO: block friend
            block.setOnAction(e -> {
            });
    }

    @Override
    public void reload() {
        Response response = CurrentUser.chatController.getFriend();
        FriendList friendList = (FriendList) response.info;
        for(Friend friend: friendList.friends){
            if (!friend.pending && !friend.blocked) {
                currentSet.add(friend.friendUsername);
                if (!removeSet.contains(friend.friendUsername)) {
                    obsList.add(new ListCellFriendItem(friend.friendUsername));
                }
            }
        }
        removeSet.removeAll(currentSet);
        for (String username: removeSet) {
            obsList.removeIf(item -> username.equals(item.getLabelText()));
        }
        removeSet.clear();
        removeSet.addAll(currentSet);
        currentSet.clear();
    }
}

package main;

import container.Friend;
import container.FriendList;
import container.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import util.CUser;
import util.Maps;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class FriendListSlideController implements Initializable {

    @FXML
    private ListView<ListCellItem> listView;
    private final ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();
    private final Set<String> removeSet = new HashSet<>();
    private final Set<String> currentSet = new HashSet<>();

    private static final String[] selectedFriend = { "" };

    // build context menu for right click
    private static final ContextMenu contextMenu = new ContextMenu();
    static {
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
    }

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
        reload();
    }

    public void reload() {
        Response response = CUser.chatController.getFriend();
        FriendList friendList = (FriendList) response.info;
        for(Friend friend: friendList.friends){
            if (!friend.pending && !friend.blocked) {
                currentSet.add(friend.friendUsername);
                if (!removeSet.contains(friend.friendUsername)) {
                    // add anything isn't in the list
                    obsList.add(new ListCellFriendItem(friend.friendUsername));
                }
            }
        }
        // remove those be deleted from the list
        removeSet.removeAll(currentSet);
        for (String username: removeSet) {
            obsList.removeIf(item -> username.equals(item.getLabelText()));
        }
        removeSet.clear();
        removeSet.addAll(currentSet);
        currentSet.clear();
    }
}

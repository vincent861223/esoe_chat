package main;

import container.Friend;
import container.FriendList;
import container.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import util.CurrentUser;
import util.Maps;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NewChatDialogController implements Initializable, ListviewController {

    @FXML
    private ListView<ListCellItem> listView;

    private final ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();
    private final Set<String> removeSet = new HashSet<>();
    private final Set<String> currentSet = new HashSet<>();
    final Set<String> members = new HashSet<>();
    final Set<ListCellNewChatItem> chatItems = new HashSet<>();

    private ChatListSlideController chatListSlideController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setItems(obsList);
        listView.setCellFactory(p -> new ListViewCell());
        chatListSlideController = (ChatListSlideController) Maps.controllers.get(Maps.CHAT_LIST);
    }

    @FXML
    void cancelDialog(ActionEvent event) {
        members.clear();
        for (ListCellNewChatItem item: chatItems) {
            item.toggleOff();
        }
        chatListSlideController.closeDialog();
    }

    @FXML
    void createNewChatroom(ActionEvent event) throws IOException {
        Maps.createNewChatroom(members.toArray(new String[0]));
        members.clear();
        for (ListCellNewChatItem item: chatItems) {
            item.toggleOff();
        }
        chatListSlideController.closeDialog();
        chatListSlideController.reload();
    }

    void addMember(String username, ListCellNewChatItem obj) {
        chatItems.add(obj);
        members.add(username);
    }

    void removeMember(String username, ListCellNewChatItem obj) {
        chatItems.remove(obj);
        members.remove(username);
    }

    @Override
    public void reload() {
        members.clear();

        Response response = CurrentUser.chatController.getFriend();
        FriendList friendList = (FriendList) response.info;

        for (Friend friend : friendList.friends) {
            if (!friend.pending && !friend.blocked) {
                currentSet.add(friend.friendUsername);
                if (!removeSet.contains(friend.friendUsername)) {
                    obsList.add(new ListCellNewChatItem(friend.friendUsername));
                }
            }
        }
        removeSet.removeAll(currentSet);
        for (String username : removeSet) {
            obsList.removeIf(item -> username.equals(item.getLabelText()));
        }
        removeSet.clear();
        removeSet.addAll(currentSet);
        currentSet.clear();
    }
}

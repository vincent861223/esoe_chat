package main;

import container.Friend;
import container.FriendList;
import container.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import util.CurrentUserInfo;
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
    final Set<String> members = new HashSet<>();

    private ChatListSlideController chatListSlideController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        chatListSlideController = (ChatListSlideController) Maps.controllers.get(Maps.CHAT_LIST);
    }

    @FXML
    void cancelDialog(ActionEvent event) {
        chatListSlideController.closeDialog();
    }

    @FXML
    void createNewChatroom(ActionEvent event) throws IOException {
        Maps.createNewChatroom(members.toArray(new String[0]));
        members.clear();
        chatListSlideController.closeDialog();
        chatListSlideController.reload();
    }

    void addMember(String username) { members.add(username); }

    void removeMember(String username) { members.remove(username); }

    @Override
    public void reload() {
        obsList.clear();
        Response response = CurrentUserInfo.chatController.getFriend();
        FriendList friendList = (FriendList) response.info;
        for(Friend friend: friendList.friends){
            if (!friend.getPending() && !friend.getBlocked())
                obsList.add(new ListCellNewChatItem(friend.getFriendUsername()));
        }
        listView.getItems().clear();
        listView.getItems().addAll(obsList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setCellFactory(p -> {
            ListCell<ListCellItem> cell = new ListViewCell();

            // TODO: check here later (if there are nothing to do, returns immediately.)

            return cell;
        });
    }
}

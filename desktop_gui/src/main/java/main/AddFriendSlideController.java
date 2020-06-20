package main;

import com.jfoenix.controls.JFXDialog;
import container.Friend;
import container.FriendList;
import container.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import util.CurrentUserInfo;
import util.Maps;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFriendSlideController implements Initializable, ListviewController {

    @FXML
    private ListView<ListCellItem> listView;

    private final ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();
    private JFXDialog dialog = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        reload();
    }

    @FXML
    void AddNewFriend() {
        StackPane content = (StackPane) Maps.parents.get(Maps.ADD_FRIEND_DIALOG);
        StackPane root = (StackPane) Maps.parents.get(Maps.ROOT_STACK_PANE);
        dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }

    void closeDialog() {
        if (dialog != null) dialog.close();
    }

    @Override
    public void reload() {
        obsList.clear();
        Response response = CurrentUserInfo.chatController.getFriend();
        FriendList friendList = (FriendList) response.info;
        for(Friend friend: friendList.friends){
            if (friend.pending && !friend.blocked)
                obsList.add(new ListCellAddFriendItem(friend.friendUsername));
        }

        listView.getItems().clear();
        listView.getItems().addAll(obsList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(p -> {

            ListCell<ListCellItem> cell = new ListViewCell();

            // FIXIT: duplicate item after confirm a friend when there were more than 1 cell item

            return cell;
        });

    }
}

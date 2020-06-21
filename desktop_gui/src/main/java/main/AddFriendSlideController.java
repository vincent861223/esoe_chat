package main;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import container.Friend;
import container.FriendList;
import container.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import util.CUser;
import util.Maps;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AddFriendSlideController implements Initializable {

    @FXML
    private ListView<ListCellItem> listView;

    private final ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();
    private final Set<String> removeSet = new HashSet<>();
    private final Set<String> currentSet = new HashSet<>();

    private JFXDialog dialog = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        listView.setItems(obsList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(p -> new ListViewCell());
        reload();
    }

    @FXML
    void addNewFriend() {
        StackPane content = (StackPane) Maps.parents.get(Maps.ADD_FRIEND_DIALOG);
        StackPane root = (StackPane) Maps.parents.get(Maps.ROOT_STACK_PANE);
        Label lblMsg = (Label)content.lookup("#lblMsg");
        JFXTextField textField = (JFXTextField) content.lookup("#tfUsername");

        // show add friend dialog
        dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.TOP);
        dialog.setOnDialogClosed(e -> {
            // reset the content of the dialog on close
            lblMsg.setVisible(false);
            textField.setText("");
        });
        dialog.show();
    }

    void closeDialog() {
        if (dialog != null) dialog.close();
    }

    public void reload() {
        Response response = CUser.chatController.getFriend();
        FriendList friendList = (FriendList) response.info;
        for(Friend friend: friendList.friends){
            if (friend.pending && !friend.blocked && !friend.inviteSender) {
                currentSet.add(friend.friendUsername);
                if (!removeSet.contains(friend.friendUsername)) {
                    // add anything isn't in the list
                    obsList.add(new ListCellAddFriendItem(friend.friendUsername));
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

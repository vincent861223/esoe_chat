package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import util.CUser;
import util.Maps;

import java.io.IOException;

import static util.Maps.ADD_FRIEND_LIST;

public class ListCellAddFriendItem extends ListCellItem {

    public ListCellAddFriendItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellAddFriendItem.fxml"));
        fxmlLoader.setController(this);
        try {
            hBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListCellAddFriendItem(String username) {
        this();
        label.setText(username);
    }

    @FXML
    void comfirmFriend(ActionEvent event) {
        CUser.chatController.confirmFriend(label.getText());
        ((AddFriendSlideController) Maps.controllers.get(ADD_FRIEND_LIST)).reload();
    }
}
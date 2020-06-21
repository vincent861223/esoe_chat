package main;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import util.CurrentUser;
import util.Maps;

import java.io.IOException;

import static util.Maps.ADD_FRIEND_LIST;

public class ListCellAddFriendItem extends ListCellItem {

    @FXML
    private JFXButton buttom;

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
        CurrentUser.chatController.confirmFriend(label.getText());
        ((AddFriendSlideController) Maps.controllers.get(ADD_FRIEND_LIST)).reload();
    }
}
package main;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import util.CurrentUserInfo;

import java.io.IOException;

import static main.MainController.ADD_FRIEND_LIST;

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
    public ListCellAddFriendItem(String labelText) {
        this();
        label1.setText(labelText);
    }

    @FXML
    void comfirmFriend(ActionEvent event) {
        CurrentUserInfo.chatController.confirmFriend(label1.getText());
        ((AddFriendSlideController) MainController.controllersMap.get(ADD_FRIEND_LIST)).reload();
    }
}
package main;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.CurrentUserInfo;

import java.io.IOException;

import static main.MainController.ADD_FRIEND_LIST;

public class ListCellNewChatItem extends ListCellItem {

    @FXML
    private HBox hBox;

    @FXML
    private Label label;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton addButton;

    private NewChatDialogController listViewController;


    public ListCellNewChatItem() {
        listViewController = (NewChatDialogController) MainController.controllersMap.get(MainController.NEW_CHAT_DIALOG);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellNewChatItem.fxml"));
        fxmlLoader.setController(this);
        try {
            hBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ListCellNewChatItem(String labelText) {
        this();
        label.setText(labelText);
    }

    @FXML
    void addToChat(ActionEvent event) {
        listViewController.addMember(label.getText());
        addButton.setVisible(false);
        cancelButton.setVisible(true);
    }

    @FXML
    void removeFromChat(ActionEvent event) {
        listViewController.removeMember(label.getText());
        addButton.setVisible(true);
        cancelButton.setVisible(false);
    }

}
package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import container.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.CUser;
import util.Maps;

public class AddFriendDialogController {

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField tfUsername;

    @FXML
    private Label lblMsg;

    private static final String ALREADY_FRIEND = "Are friends already.";
    private static final String USERNAME_DOES_NOT_EXIST = "friend username does not exist";
    private static final String ALREADY_INVITED = "Already added, waiting for friend to confirm.";

    @FXML
    void textfieldKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
            addFriend();
    }

    @FXML
    void submitAddFriend(ActionEvent event) {
        addFriend();
    }

    void addFriend() {
        String username = tfUsername.getText();
        if (username.equals("")) return;
        Response response = CUser.chatController.addFriend(username);
        if (response.status.equals("OK")) {
            lblMsg.setVisible(false);
            // Set up success dialog
            VBox content = (VBox) Maps.parents.get(Maps.ALERT_DIALOG);
            Label label = (Label)content.lookup("#label");
            JFXButton button = (JFXButton)content.lookup("#button");
            label.setText("Invitation Sent!");
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            button.setOnAction(e -> {
                dialog.close();
                cancelAddFriend(null);
            });
            dialog.show();
        }
        else {
            // display error message
            if (response.msg.equals(USERNAME_DOES_NOT_EXIST)) {
                lblMsg.setText("User \"" + username + "\" does not exist.");
            }
            else if (response.msg.equals(ALREADY_FRIEND)) {
                lblMsg.setText(username + " is already your friend.");
            }
            else if (response.msg.equals(ALREADY_INVITED)) {
                lblMsg.setText("You're already invite "+ username + ".");
            }
            lblMsg.setVisible(true);
        }
    }

    @FXML
    void cancelAddFriend(ActionEvent event) {
        lblMsg.setVisible(false);
        tfUsername.setText("");
        ((AddFriendSlideController) Maps.controllers.get(Maps.ADD_FRIEND_LIST)).closeDialog();
    }

}

package main;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;

public class ChatroomController {

    @FXML
    private Label lblChatroomTitle;

    @FXML
    private FontIcon btnRingOn;

    @FXML
    private FontIcon btnRingOff;

    @FXML
    private TextField tfInputMessage;

    @FXML
    private JFXButton btnSendMessage;

    @FXML
    void RingOn(MouseEvent event) {
        btnRingOff.setVisible(false);
        btnRingOn.setVisible(true);
    }

    @FXML
    void RingOff(MouseEvent event) {
        btnRingOn.setVisible(false);
        btnRingOff.setVisible(true);
    }

    public void setChatroomTitle(String title) {
        lblChatroomTitle.setText(title);
    }
}

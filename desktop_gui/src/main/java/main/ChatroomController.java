package main;

import com.jfoenix.controls.JFXTextField;
import container.Message;
import container.MessageHistory;
import container.Response;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import util.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class ChatroomController implements Initializable {

    @FXML
    private Label lblChatroomTitle;

    @FXML
    private FontIcon btnRingOn;

    @FXML
    private FontIcon btnRingOff;

    @FXML
    private FontIcon statusIcon;

    @FXML
    private Label lblChatroomStatus;


    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox messageBox;

    @FXML
    private JFXTextField inputMessage;

    @FXML
    private JFXButton btnSendMessage;

    private Map<String, String> members = new HashMap<>();
    private String chatroomID;
    private String chatroomTitle;
    private int memberCount;
    private int loadedHistoryCount;
    private String previousSender;
    private String lastMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            // TODO: set chatroom status
            setChatroomTitle();
            loadHistory();
        });
    }

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

    @FXML
    void clickedSend(ActionEvent event) {
        sendMessage();
    }

    @FXML
    void tfKeyPressed(KeyEvent event) {
        System.out.println("Key pressed");
        if(event.getCode().equals(KeyCode.ENTER))
            sendMessage();
    }

    @FXML
    void sendMessage() {
        CurrentUserInfo.chatController.sendMessage(chatroomID, inputMessage.getText());
        inputMessage.setText("");
    }

    public void setChatroomTitle() {
        // TODO: Chatroom Title
//        Response response = CurrentUserInfo.chatController.getChatroomName(chatroomID);
//        StringTokenizer tokens = new StringTokenizer(response.msg, " ");
//        String username;
//        while(tokens.hasMoreTokens()) {
//            username = tokens.nextToken();
//            members.put(username);
//        }

        String title = chatroomID;
        lblChatroomTitle.setText(title);
    }

    public void loadHistory() {
        Platform.runLater(() -> {
            Response response = CurrentUserInfo.chatController.getHistory(chatroomID);
            MessageHistory messageHistory = (MessageHistory) response.info;
            int numberOfMessages = messageHistory.messages.size();
            for (int i = loadedHistoryCount; i < numberOfMessages; i++) {
                Message msg = messageHistory.messages.get(i);
                String timestamp = msg.timestamp;
                String username = CurrentUserInfo.chatController.getUsername(msg.senderID).msg;
                if (username.equals(CurrentUserInfo.getUsername())) {
                    messageBox.getChildren().add(new RightMessageUnit(msg.msg, timestamp));
                }
                else {
                    if (username.equals(previousSender))
                        messageBox.getChildren().add(new LeftMessageUnit(msg.msg, timestamp));
                    else {
                        messageBox.getChildren().add(new LeftNameMessageUnit(msg.msg, timestamp, username));
                        previousSender = username;
                    }
                }
                lastMessage = msg.msg;
            }
            loadedHistoryCount = numberOfMessages;
            Maps.chatroomListItems.get(chatroomID).setLabel2(lastMessage);
            scrollPane.vvalueProperty().bind(messageBox.heightProperty());
        });
    }

    public void setChatroomID(String chatroomID) {
        this.chatroomID = chatroomID;
    }

}
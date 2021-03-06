package util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class MessageUnit extends VBox {

    @FXML
    private Label timestamp;

    @FXML
    private Label message;


    public void setTimestamp(String timestamp) {
        this.timestamp.setText(timestamp);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

}

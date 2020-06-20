package util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class LeftNameMessageUnit extends MessageUnit {

    @FXML
    private Label username;

    public LeftNameMessageUnit() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leftNameMessageUnit.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LeftNameMessageUnit(String message, String timestamp, String username) {
        this();
        setMessage(message);
        setTimestamp(timestamp);
        setUsername(username);
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

}

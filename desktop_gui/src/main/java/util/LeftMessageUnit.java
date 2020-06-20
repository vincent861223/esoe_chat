package util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LeftMessageUnit extends MessageUnit {

    @FXML
    private VBox vBox;

    @FXML
    private Label username;

    public LeftMessageUnit() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leftMessageUnit.fxml"));
        fxmlLoader.setController(this);
        try {
            vBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LeftMessageUnit(String message, String timestamp, String username) {
        // TODO: check after finish send message
        if (message.equals("")) setMessage("just weird space here");
        else setMessage(message);

        setTimestamp(timestamp);
        setUsername(username);
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

}

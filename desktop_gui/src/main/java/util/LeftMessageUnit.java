package util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LeftMessageUnit extends MessageUnit {

    public LeftMessageUnit() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leftMessageUnit.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LeftMessageUnit(String message, String timestamp) {
        this();
        setMessage(message);
        setTimestamp(timestamp);
    }

}
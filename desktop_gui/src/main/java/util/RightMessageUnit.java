package util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RightMessageUnit extends MessageUnit {

    @FXML
    private VBox vBox;

    public RightMessageUnit() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rightMessageUnit.fxml"));
        fxmlLoader.setController(this);
        try {
            vBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RightMessageUnit(String message, String timestamp) {
        setMessage(message);
        setTimestamp(timestamp);
    }

}

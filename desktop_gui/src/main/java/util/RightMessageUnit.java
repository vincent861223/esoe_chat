package util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

// Right message unit (when the sender is myself)
public class RightMessageUnit extends MessageUnit {

    public RightMessageUnit() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rightMessageUnit.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RightMessageUnit(String message, String timestamp) {
        this();
        setMessage(message);
        setTimestamp(timestamp);
    }

}

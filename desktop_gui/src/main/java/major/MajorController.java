package major;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MajorController implements Initializable {

    private static String userID = "NOT YOU";

    @FXML
    private Label test;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        test.setText(userID);
    }

    public static void setUserID(String userID) {
        MajorController.userID = userID;
    }
}

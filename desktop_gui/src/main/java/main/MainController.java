package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import util.CurrentUserInfo;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainController extends CurrentUserInfo implements Initializable {

    double x, y;

    @FXML
    private FontIcon btnRingOn;

    @FXML
    private FontIcon btnRingOff;

    @FXML
    private FontIcon btnFriendList;

    @FXML
    private FontIcon btnChatList;

    @FXML
    private FontIcon btnAddFriend;

    @FXML
    private FontIcon btnSetting;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void main(String[] args) {
        launch(args);
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
    void Dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void Pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void closeClicked(MouseEvent event) throws NullPointerException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // FormController.popOver.setAnimated(false);
        stage.close();
    }
    @FXML
    void maximizeClicked(MouseEvent event) {

    }

    // FIXME: this doesn't function properly on macOS Catalina since java doesn't fix it
    @FXML
    void minimizeClicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}

package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import login.FormController;
import login.Transition;
import org.kordamp.ikonli.javafx.FontIcon;
import util.CurrentUserInfo;
import util.StageMap;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainController implements Initializable {
//public class MainController extends CurrentUserInfo implements Initializable {

    double x, y;

    private static HashMap<String, Parent> slidesMap = new HashMap<>();
    private Parent currentSlide = null;

    private static final String FRIEND_LIST = "friendListSlide";
    private static final String CHAT_LIST = "chatListSlide";
    private static final String ADD_FRIEND_LIST = "addFriendSlide";
    private static final String SETTING = "settingSlide";

    @FXML
    private FontIcon btnRingOn;

    @FXML
    private FontIcon btnRingOff;

    @FXML
    private StackPane slidePane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater( () -> {
            try {
                CurrentUserInfo.testLogin();
                loadPane(FRIEND_LIST);
                loadPane(CHAT_LIST);
                loadPane(ADD_FRIEND_LIST);
                loadPane(SETTING);
                currentSlide = slidesMap.get(FRIEND_LIST);
                currentSlide.setVisible(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }


    private void loadPane(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        slidePane.getChildren().add(root);
        root.setVisible(false);
        slidesMap.put(fxml, root);

    }

    @FXML
    void clickedAddFriend(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = slidesMap.get(ADD_FRIEND_LIST);
        currentSlide.setVisible(true);
    }

    @FXML
    void clickedChatList(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = slidesMap.get(CHAT_LIST);
        currentSlide.setVisible(true);
    }

    @FXML
    void clickedFriendList(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = slidesMap.get(FRIEND_LIST);
        currentSlide.setVisible(true);
    }

    @FXML
    void clickedSetting(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = slidesMap.get(SETTING);
        currentSlide.setVisible(true);
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

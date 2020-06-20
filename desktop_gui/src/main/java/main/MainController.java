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
import org.kordamp.ikonli.javafx.FontIcon;
import util.CurrentUserInfo;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainController implements Initializable {

    private static double x, y;

    static HashMap<String, Parent> parentsMap = new HashMap<>();
    static HashMap<String, Object> controllersMap = new HashMap<>();
    private Parent currentSlide = null;

    static final String FRIEND_LIST = "friendListSlide";
    static final String CHAT_LIST = "chatListSlide";
    static final String ADD_FRIEND_LIST = "addFriendSlide";
    static final String SETTING = "settingSlide";
    static final String ROOT_STACK_PANE = "rootStackPane";
    static final String ADD_FRIEND_DIALOG = "addFriendDialog";
    static final String ALERT_DIALOG = "alertDialog";
    static final String NEW_CHAT_DIALOG = "newChatDialog";

    @FXML
    private StackPane rootStackPane;

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
                loadSlidePane(FRIEND_LIST);
                loadSlidePane(CHAT_LIST);
                loadSlidePane(ADD_FRIEND_LIST);
                loadSlidePane(SETTING);
                currentSlide = parentsMap.get(FRIEND_LIST);
                currentSlide.setVisible(true);
                parentsMap.put(ROOT_STACK_PANE, rootStackPane);
                loadDialog(ADD_FRIEND_DIALOG);
                loadDialog(ALERT_DIALOG);
                loadDialog(NEW_CHAT_DIALOG);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }


    private void loadDialog(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        parentsMap.put(fxml, loader.load());
        controllersMap.put(fxml, loader.getController());
    }
    private void loadSlidePane(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        slidePane.getChildren().add(root);
        root.setVisible(false);
        parentsMap.put(fxml, root);
        controllersMap.put(fxml, loader.getController());
    }

    @FXML
    void clickedAddFriend(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = parentsMap.get(ADD_FRIEND_LIST);
        currentSlide.setVisible(true);
        ((AddFriendSlideController) controllersMap.get(ADD_FRIEND_LIST)).reload();
    }

    @FXML
    void clickedChatList(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = parentsMap.get(CHAT_LIST);
        currentSlide.setVisible(true);
        ((ChatListSlideController) controllersMap.get(CHAT_LIST)).reload();
    }

    @FXML
    void clickedFriendList(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = parentsMap.get(FRIEND_LIST);
        currentSlide.setVisible(true);
        ((FriendListSlideController) controllersMap.get(FRIEND_LIST)).reload();
    }

    @FXML
    void clickedSetting(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = parentsMap.get(SETTING);
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

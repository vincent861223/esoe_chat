package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.CurrentUser;
import util.Maps;
import util.UpdateHistoryThread;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainController implements Initializable {

    private static double x, y;

    private Parent currentSlide = null;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane slidePane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateHistoryThread updateHistoryThread = new UpdateHistoryThread();
        updateHistoryThread.start();

        Platform.runLater( () -> {
            try {
                CurrentUser.testLogin();
                loadSlidePane(Maps.FRIEND_LIST);
                loadSlidePane(Maps.CHAT_LIST);
                loadSlidePane(Maps.ADD_FRIEND_LIST);
                loadSlidePane(Maps.SETTING);
                currentSlide = Maps.parents.get(Maps.FRIEND_LIST);
                currentSlide.setVisible(true);
                Maps.parents.put(Maps.ROOT_STACK_PANE, rootStackPane);
                Maps.setBorderPane(borderPane);
                loadDialog(Maps.ADD_FRIEND_DIALOG);
                loadDialog(Maps.NEW_CHAT_DIALOG);
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
        Maps.parents.put(fxml, loader.load());
        Maps.controllers.put(fxml, loader.getController());
    }

    private void loadSlidePane(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        slidePane.getChildren().add(root);
        root.setVisible(false);
        Maps.parents.put(fxml, root);
        Maps.controllers.put(fxml, loader.getController());
    }

    @FXML
    void clickedAddFriend(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = Maps.parents.get(Maps.ADD_FRIEND_LIST);
        currentSlide.setVisible(true);
        ((AddFriendSlideController) Maps.controllers.get(Maps.ADD_FRIEND_LIST)).reload();
    }

    @FXML
    void clickedChatList(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = Maps.parents.get(Maps.CHAT_LIST);
        currentSlide.setVisible(true);
        ((ChatListSlideController) Maps.controllers.get(Maps.CHAT_LIST)).reload();
    }

    @FXML
    void clickedFriendList(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = Maps.parents.get(Maps.FRIEND_LIST);
        currentSlide.setVisible(true);
        ((FriendListSlideController) Maps.controllers.get(Maps.FRIEND_LIST)).reload();
    }

    @FXML
    void clickedSetting(ActionEvent event) {
        currentSlide.setVisible(false);
        currentSlide = Maps.parents.get(Maps.SETTING);
        currentSlide.setVisible(true);
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
        CurrentUser.chatController.logout();
        System.exit(0);
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

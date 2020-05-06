package login;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    double x, y;

    @FXML
    private StackPane boxPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater( () -> {

            try {
                loadPane("loginBox");
                fadeIn("#loginBox");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadPane(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
        root.translateXProperty().set(boxPane.getWidth());
        boxPane.getChildren().add(root);
    }

    private void fadeIn(String cssID) {
        Node node = boxPane.lookup(cssID);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void fadeOut(String cssID) {
        Node node = boxPane.lookup(cssID);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(node.translateXProperty(),600 - boxPane.getLayoutX(), Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    void handleGoToSignUp(ActionEvent event) throws IOException {
        fadeOut("#loginBox");
        loadPane("signUpBox");
        fadeIn("#signUpBox");
    }

    @FXML
    void barDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void barPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void closeClicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimizeClicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

}

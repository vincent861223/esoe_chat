package util;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class NotificationUnit extends HBox {

    @FXML
    private HBox root;

    @FXML
    private Label lblSender;

    @FXML
    private Label lblMessage;

    private CustomStage stage;
    private Animation animation;

    public NotificationUnit() {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notificationUnit.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // set up notification stage, position & animation
            stage = new CustomStage(root, StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(true);
            stage.setLocation(stage.getBottomRight());
            animation = new Animation(stage);
        });

    }

    public NotificationUnit(String sender, String message) {
        this();
        Platform.runLater(() -> {
            lblSender.setText(sender + " says");
            lblMessage.setText(message);
        });
    }

    public void show(Duration duration) {
        Platform.runLater(() -> {
            if (!isShowing()) {
                stage.show();
                animation.playSequential(duration);
            }
        });
    }

    public boolean isShowing() {
        return animation.isShowing();
    }

}

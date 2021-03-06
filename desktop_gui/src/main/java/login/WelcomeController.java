package login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.CUser;
import util.Maps;

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
                // load all panes in the login stage
                loadPane("loginBox");
                loadPane("signUpBox");
                Transition.fadeIn(boxPane,"#loginBox");
                loadDialog(Maps.ALERT_DIALOG);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadDialog(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(CUser.class.getResource(fxml + ".fxml"));
        Maps.parents.put(fxml, loader.load());
        Maps.controllers.put(fxml, loader.getController());
    }

    private void loadPane(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        Platform.runLater(() -> {
            FormController controller = loader.getController();
            if (controller != null) {
                FormController.setBoxPane(boxPane);
            }
        });
        root.translateXProperty().set(600);
        boxPane.getChildren().add(root);
    }

    @FXML
    void handleGoToSignUp(ActionEvent event)  {
        Transition.fadeOut(boxPane,"#loginBox");
        Transition.fadeIn(boxPane, "#signUpBox");
    }

    // Mouse Drag
    @FXML
    void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    // Set up (x, y) location for Dragging
    @FXML
    void titleBarPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void closeClicked(MouseEvent event) throws NullPointerException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FormController.popOver.setAnimated(false);
        stage.close();
        System.exit(0);
    }

    // FIXME: this doesn't function properly on macOS Catalina since javafx doesn't fix it
    @FXML
    void minimizeClicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

}

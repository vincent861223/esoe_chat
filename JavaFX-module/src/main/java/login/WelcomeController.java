package login;

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
                loadPane("signUpBox");
                Transition.fadeIn(boxPane,"#loginBox");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadPane(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        FormController controller = loader.getController();
        controller.setBoxPane(boxPane);
        root.translateXProperty().set(600);
        boxPane.getChildren().add(root);
    }

    @FXML
    void handleGoToSignUp(ActionEvent event)  {
        Transition.fadeOut(boxPane,"#loginBox");
        Transition.fadeIn(boxPane, "#signUpBox");
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
    void closeClicked(MouseEvent event) throws NullPointerException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //FIXIT: this doesn't function properly on macOS Catalina since java doesn't fix it
    @FXML
    void minimizeClicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

}

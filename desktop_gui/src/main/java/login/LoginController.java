package login;

import container.Response;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.MainController;
import org.controlsfx.control.PopOver;
import util.CUser;
import util.GuiException;
import util.Maps;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends FormController implements Initializable {


    private static final String SAVED_USERNAME = "savedUsername";
    private static final String SAVED_PASSWORD = "savedPassword";

    @FXML
    private JFXTextField inputUsername;

    @FXML
    private JFXPasswordField inputPassword;

    @FXML
    private JFXCheckBox btnRememberMe;

    @FXML
    private JFXButton btnLogin;

    public LoginController() throws IOException { super(); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // display saved info
        displaySavedInfo();
        // Button will be enable if both field are not empty
        btnLogin.disableProperty().bind((
            inputUsername.textProperty().isNotEmpty().and(
            inputPassword.textProperty().isNotEmpty()
            ).not()
        ));
    }

    @FXML
    void handleLoginClicked(ActionEvent event) {
        String username;
        String password;
        try {
            username = inputUsername.getText();
            password = inputPassword.getText();
            Response response = CUser.chatController.login(username, password);
            if (response == null) {
                throw new GuiException("Server Offline");
            }
            else if (response.getStatus().equals("Failed")) {
                throw new GuiException(response.getMsg());
            }
            else {
                // store username & password in Preference
                if (btnRememberMe.isSelected()) {
                    CUser.userPrefs.put(SAVED_USERNAME, username);
                    CUser.userPrefs.put(SAVED_PASSWORD, password);
                } else {
                    CUser.userPrefs.put(SAVED_USERNAME, "");
                    CUser.userPrefs.put(SAVED_PASSWORD, "");
                }

                try {
                    // Create Main Screen
                    CUser.setUsername(username);
                    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(MainController.class.getResource("styles/main.css").toExternalForm());
                    Stage newStage = new Stage();
                    newStage.setScene(scene);
                    newStage.initStyle(StageStyle.TRANSPARENT);
                    newStage.setTitle("ESOE CHAT");
                    newStage.show();
                    Maps.stages.put(Maps.MAIN_STAGE, newStage);
                    Stage loginStage = Maps.stages.getOrDefault(Maps.LOGIN_STAGE, null);
                    if (loginStage != null) {
                        loginStage.hide();
                    }
                } catch (Exception e) {
                    System.err.println( "ERROR: "+ e.getMessage());
                }
            }

        } catch (GuiException e) {
            ((Label) popOver.getContentNode()).setText(e.getErrMessage());
            popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
            popOver.show(btnLogin, -5);
        }

    }

    private void displaySavedInfo() {
        String account = CUser.userPrefs.get(SAVED_USERNAME, "");
        String password = CUser.userPrefs.get(SAVED_PASSWORD, "");
        if (account.length() != 0 && password.length() != 0) {
            inputUsername.setText(account);
            inputPassword.setText(password);
            btnRememberMe.setSelected(true);
            inputUsername.focusedProperty().addListener( (v, oldValue, newValue) -> {
                Platform.runLater( () -> {
                    inputUsername.selectEnd();
                    inputUsername.deselect();
                });
            });
        }
    }
}

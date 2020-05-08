package login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController extends FormController implements Initializable {

    Preferences userPreferences = Preferences.userRoot();
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

    @FXML
    private Label lblForgetPassword;

    public LoginController() throws IOException { super(); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displaySavedInfo();
        btnLogin.disableProperty().bind((
            inputUsername.textProperty().isNotEmpty().and(
            inputPassword.textProperty().isNotEmpty()
            ).not()
        ));
    }

    //TODO: forget password
    @FXML
    void handleForgetPasswordClicked(MouseEvent event) {

    }

    @FXML
    void handleLoginClicked(ActionEvent event) throws IOException {
        String username;
        String password;
        try {
            username = inputUsername.getText();
            password = inputPassword.getText();
            response = chatController.login(username, password);
            if (response == null) {
                throw new GuiException("Server Offline");
            }
            else if (response.getStatus().equals("Failed")) {
                throw new GuiException(response.getMsg());
            }
            else {
                System.out.println("Login Successfully"); // DELETEME
                if (btnRememberMe.isSelected()) {
                    userPreferences.put(SAVED_USERNAME, username);
                    userPreferences.put(SAVED_PASSWORD, password);
                } else {
                    userPreferences.put(SAVED_USERNAME, "");
                    userPreferences.put(SAVED_PASSWORD, "");
                }
//
//            frame.dispose();
//            MainFrame frmMain = new MainFrame();
//            frmMain.setVisible(true);

                //TODO: newWindow
                //Login.newWindow();


            }

        } catch (GuiException e) {
            ((Label) popOver.getContentNode()).setText(e.getErrMessage());
            popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
            popOver.show(btnLogin, -5);
        }
    }

    private void displaySavedInfo() {
        String account = userPreferences.get(SAVED_USERNAME, "");
        String password = userPreferences.get(SAVED_PASSWORD, "");
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

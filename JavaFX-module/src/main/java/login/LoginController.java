package login;
import client.ChatController;
import container.Response;
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
import old.GuiException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {

    ChatController chatController = new ChatController("127.0.0.1", 12345);
    Response response;
    Preferences userPreferences = Preferences.userRoot();
    private static final String SAVED_USERNAME = "savedUsername";
    private static final String SAVED_PASSWORD = "savedPassword";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displaySavedInfo();
    }

    @FXML
    private JFXTextField inputUsername;

    @FXML
    private JFXPasswordField inputPassword;

    @FXML
    private JFXCheckBox btnRememberMe;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Label loginMsg;

    @FXML
    private Label lblForgetPassword;

    @FXML
    void handleForgetPasswordClicked(MouseEvent event) {

    }

    @FXML
    void handleLoginClicked(ActionEvent event) {
        String account;
        String password;
        try {
            account = inputUsername.getText();
            password = inputPassword.getText();
            response = chatController.login(account, password);
            if (response.getStatus().equals("Failed")) {
                throw new GuiException(response.getMsg());
            }
            loginMsg.setVisible(true);
            loginMsg.setText("Login Success");

            if (btnRememberMe.isSelected()) {
                userPreferences.put(SAVED_USERNAME, account);
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

        } catch (GuiException e) {
            loginMsg.setVisible(true);
            loginMsg.setText(e.getErrMessage());
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
//            btnLogin.setEnabled(true);
        }
    }

}

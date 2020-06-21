package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import container.Response;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import util.CUser;
import util.GuiException;
import util.Maps;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController extends FormController implements Initializable {

    Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    BooleanProperty isEmailValid = new SimpleBooleanProperty(false);

    @FXML
    private JFXTextField inputUsername;

    @FXML
    private JFXTextField inputEmail;

    @FXML
    private JFXPasswordField inputPassword;

    @FXML
    private JFXPasswordField inputConfirmPassword;

    @FXML
    private JFXButton btnSignUp;

    public SignUpController() throws IOException {
        super();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            // button will be enable when all field are not empty or invalid
            btnSignUp.disableProperty().bind((
                    inputUsername.textProperty().isNotEmpty().and(
                            inputPassword.textProperty().isNotEmpty().and(
                                    inputConfirmPassword.textProperty().isNotEmpty().and(
                                            inputEmail.textProperty().isNotEmpty()).and(
                                            inputPassword.textProperty().isEqualTo(inputConfirmPassword.textProperty()).and(
                                                    isEmailValid
                                            )
                                    )
                            )
                    ).not()
            ));

            // show popOver when two password fields do not match
            inputConfirmPassword.focusedProperty().addListener((v, oldValue, newValue) -> {
                if (newValue) {
                    popOver.hide();
                } else {
                    if (!matchPassword()) {
                        ((Label) popOver.getContentNode()).setText("Passwords do not match");
                        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                        popOver.show(inputConfirmPassword, -5);
                    }
                }
            });

            // show popOver when e-mail format is invalid
            inputEmail.setOnKeyTyped(v -> {
                Matcher matcher = pattern.matcher(inputEmail.getText());
                if (!matcher.matches()) {
                    isEmailValid.set(false);
                } else {
                    isEmailValid.set(true);
                }
            });
            inputEmail.focusedProperty().addListener((v, oldValue, newValue) -> {
                if (newValue) {
                    popOver.hide();
                } else {
                    if (!isEmailValid.getValue()) {
                        ((Label) popOver.getContentNode()).setText("Invalid e-mail");
                        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                        popOver.show(inputEmail, -5);
                    }
                }
            });
        });
    }

    @FXML
    void goToLogin() {
        Transition.fadeOut(boxPane, "#signUpBox");
        Transition.fadeIn(boxPane, "#loginBox");
    }

    @FXML
    void handleSignUp(ActionEvent event) {
        String username;
        String email;
        String password;
        try {
            username = inputUsername.getText();
            email = inputEmail.getText();
            password = inputPassword.getText();
            Response response = CUser.chatController.register(username, email, password);
            if (response == null) {
                throw new GuiException("Server Offline");
            } else if (response.getStatus().equals("Failed")) {
                throw new GuiException(response.getMsg());
            }

            // Set up success message dialog
            VBox content = (VBox) Maps.parents.get(Maps.ALERT_DIALOG);
            Label label = (Label)content.lookup("#label");
            JFXButton button = (JFXButton)content.lookup("#button");
            label.setText("Sign up completed!");
            JFXDialog dialog = new JFXDialog(boxPane, content, JFXDialog.DialogTransition.TOP);
            button.setOnAction(a -> {
                dialog.close();
                goToLogin();
            });
            dialog.show();
            dialog.setOnDialogClosed(e -> {
                // clean textfields after sign up successfully
                inputUsername.setText("");
                inputEmail.setText("");
                inputPassword.setText("");
                inputConfirmPassword.setText("");
            });

        } catch (GuiException e) {
            ((Label) popOver.getContentNode()).setText(e.getErrMessage());
            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
            popOver.show(btnSignUp, -5);
        }
    }

    @FXML
    void haveAccountClicked(MouseEvent event) {
        goToLogin();
    }

    private boolean matchPassword() {
        return inputConfirmPassword.getText().equals(inputPassword.getText());
    }


}

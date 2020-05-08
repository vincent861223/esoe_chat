package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends FormController implements Initializable {

      // TODO: show validation information
//    private static final String eMailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
//    ValidationSupport eMailValidation = new ValidationSupport();
//    ValidationSupport passwordValidation = new ValidationSupport();

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

    public SignUpController() throws IOException { super(); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            btnSignUp.disableProperty().bind((
                 inputUsername.textProperty().isNotEmpty().and(
                 inputPassword.textProperty().isNotEmpty().and(
                 inputConfirmPassword.textProperty().isNotEmpty().and(
                 inputEmail.textProperty().isNotEmpty()).and(
                 inputPassword.textProperty().isEqualTo(inputConfirmPassword.textProperty())
                 )
                 )
                 ).not()
            ));
            // TODO: show validation information
//            eMailValidation.setValidationDecorator(new StyleClassValidationDecoration());
//            passwordValidation.setValidationDecorator(new StyleClassValidationDecoration());
//            eMailValidation.registerValidator(inputEmail, Validator.createRegexValidator("Invalid e-mail format",  eMailRegex, Severity.ERROR));
//            passwordValidation.registerValidator(inputConfirmPassword, Validator.createPredicateValidator(o -> matchPassword(), "Password does not match"));
        });
    }

    @FXML
    void goToLogin() {
        Transition.fadeOut(boxPane, "#signUpBox");
        Transition.fadeIn(boxPane, "#loginBox");
    }

    @FXML
    void handleSignUp(ActionEvent event) throws IOException {
        String username;
        String email;
        String password;
        try {
            username = inputUsername.getText();
            email = inputEmail.getText();
            password = inputPassword.getText();
            response = chatController.register(username, email, password);
            if (response == null) {
                throw new GuiException("Server Offline");
            } else if (response.getStatus().equals("Failed")) {
                throw new GuiException(response.getMsg());
            }
            // TODO: show validation information -> Pop up window
            System.out.println("Sign up Successfully");
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

    private void setValidationDeco(ValidationSupport v) {

    }

}

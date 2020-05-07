package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SignUpController extends FormController implements Initializable {

    //TODO: display validator
//    private static final String eMailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
//    ValidationSupport eMailValidation = new ValidationSupport();
//    ValidationSupport passwordValidation = new ValidationSupport();

    PopOver popOver;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            popOver = new PopOver((Node) FXMLLoader.load(getClass().getResource("popupBox.fxml")));
            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
            popOver.getRoot().getStylesheets().add(getClass().getResource("styles/popover.css").toExternalForm());
            popOver.setArrowSize(7.5);
            popOver.setArrowIndent(5.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            //TODO: Pop up window
            System.out.println("Sign up Successfully");
        } catch (GuiException e) {
            //((Label) popOver.getContentNode().lookup("#msg")).setText(e.getErrMessage());
            ((Label) popOver.getContentNode()).setText(e.getErrMessage());
            popOver.show(btnSignUp);
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

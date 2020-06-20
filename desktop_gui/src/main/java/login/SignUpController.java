package login;

import com.jfoenix.controls.*;
import container.Response;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;
import org.kordamp.ikonli.javafx.FontIcon;

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

    public SignUpController() throws IOException { super(); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
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

            inputConfirmPassword.focusedProperty().addListener((v, oldValue, newValue) -> {
                if (newValue) {
                    popOver.hide();
                }
                else {
                    ((Label) popOver.getContentNode()).setText("Passwords do not match");
                    popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
                    popOver.show(inputConfirmPassword, -5);
                }
            });

            inputEmail.setOnKeyTyped(v -> {
                Matcher matcher = pattern.matcher(inputEmail.getText());
                if (!matcher.matches()) {
                    isEmailValid.set(false);
                }
                else {
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
            Response response = chatController.register(username, email, password);
            if (response == null) {
                throw new GuiException("Server Offline");
            } else if (response.getStatus().equals("Failed")) {
                throw new GuiException(response.getMsg());
            }

            VBox vbox = new VBox();
            vbox.setPrefSize(250, 160);
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(5);
            vbox.getStylesheets().add(getClass().getResource("styles/signupbox.css").toExternalForm());
            FontIcon icon = new FontIcon("mdi-checkbox-marked-circle-outline:35:#F79D84");
            Label lbl = new Label("You have signed up successfully!");
            lbl.getStyleClass().add("dialog-label");
            JFXButton button = new JFXButton("Go to login");
            button.getStyleClass().add("dialog-button");
            vbox.getChildren().addAll(icon, lbl, button);
            vbox.setMargin(button, new Insets(10, 0, 0, 0));

            JFXDialog dialog = new JFXDialog(boxPane, vbox, JFXDialog.DialogTransition.TOP);
            button.setOnAction(a -> {
                dialog.close();
                goToLogin();
            });
            dialog.show();

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

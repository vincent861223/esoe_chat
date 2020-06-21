package main;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import util.CUser;
import util.Maps;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingSlideController implements Initializable {

    @FXML
    private JFXToggleButton toggleNotification;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleNotification.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleNotification.isSelected()) {
                toggleNotification.setText("Notification On");
                CUser.userPrefs.putBoolean(CUser.NOTIFICATION_PREF, true);
            }
            else {
                toggleNotification.setText("Notification Off");
                CUser.userPrefs.putBoolean(CUser.NOTIFICATION_PREF, false);
            }
        });
        // If It's the first time run this program, default = notification on(=true)
        toggleNotification.setSelected(CUser.userPrefs.getBoolean(CUser.NOTIFICATION_PREF, true));
    }

    // logout: back to lonin screen
    @FXML
    void logoutClicked(ActionEvent event) {
        Stage loginStage = Maps.stages.getOrDefault(Maps.LOGIN_STAGE, null);
        if (loginStage != null) {
            loginStage.show();
        }
        Stage mainStage = Maps.stages.getOrDefault(Maps.MAIN_STAGE, null);
        if(mainStage != null) {
            mainStage.close();
        }
        CUser.chatController.logout();
    }
}

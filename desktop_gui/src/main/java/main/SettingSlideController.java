package main;

import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import util.CurrentUser;
import util.Maps;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SettingSlideController implements Initializable {

    @FXML
    private JFXToggleButton toggleNotification;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleNotification.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleNotification.isSelected()) {
                toggleNotification.setText("Notification On");
                CurrentUser.userPrefs.putBoolean(CurrentUser.NOTIFICANTION_PREF, true);
            }
            else {
                toggleNotification.setText("Notification Off");
                CurrentUser.userPrefs.putBoolean(CurrentUser.NOTIFICANTION_PREF, false);
            }
        });
        toggleNotification.setSelected(CurrentUser.userPrefs.getBoolean(CurrentUser.NOTIFICANTION_PREF, true));
    }

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
        CurrentUser.chatController.logout();
    }
}

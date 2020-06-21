package main;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import util.CurrentUser;
import util.Maps;

public class SettingSlideController {
    @FXML
    private JFXToggleButton toggleNotification;

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

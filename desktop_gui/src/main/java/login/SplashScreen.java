package login;

import client.Main;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import util.Maps;

public class SplashScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomeWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
        Maps.stages.put(Maps.LOGIN_STAGE, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

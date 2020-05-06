package login;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;


public class Main extends Application {

    private static Scene scene;
    private static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getResource("welcomeWindow.fxml"));
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles/welcome.css").toExternalForm());
        window = primaryStage;
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle("Login");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}

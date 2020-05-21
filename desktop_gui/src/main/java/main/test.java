package main;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;
import util.StageMap;

import java.io.IOException;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
//        scene.setFill(Color.TRANSPARENT);
//        primaryStage.setScene(scene);


        VBox vbox = new VBox();
        vbox.setPrefSize(250, 160);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10));

        FontIcon icon = new FontIcon("mdi-checkbox-marked-circle-outline:35:#F79D84");
        Label lbl = new Label("You have signed up successfully!");
        lbl.setFont(new Font("Noto Sans CJK TC Light", 18));
        lbl.setWrapText(true);
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setStyle("-fx-text-fill: #686868");
        JFXButton button = new JFXButton("Go to login");
        button.setFont(new Font("Noto Sans CJK TC DemiLight", 13));
        button.setStyle("-fx-background-color: #F79D84; -fx-text-fill: white;");


        vbox.getChildren().addAll(icon, lbl, button);
        vbox.setMargin(button, new Insets(8, 0, 5, 0));

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();


    }
}

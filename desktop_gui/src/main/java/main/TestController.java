package main;


import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class TestController {

    public TestController() {
    }

    @FXML
    void keypressed(KeyEvent event) {
        System.out.println("keypressed: " + event.getCode());
    }

    @FXML
    void keyreleased(KeyEvent event) {
        System.out.println("keyreleased: " + event.getCode());
    }

    @FXML
    void keytyped(KeyEvent event) {
        System.out.println("keytyped: " + event.getCharacter());
    }

}
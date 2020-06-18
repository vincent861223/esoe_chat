package main;

import javafx.scene.layout.StackPane;

public abstract class SlideController {

    static StackPane slidePane;

    static void setSlidePane(StackPane slidePane) {
        SlideController.slidePane = slidePane;
    }
}

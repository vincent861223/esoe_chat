package login;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Transition {
    private Transition() {}

    static void fadeIn(StackPane boxPane, String cssID) {
        Node node = boxPane.lookup(cssID);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    static void fadeOut(StackPane boxPane, String cssID) {
        Node node = boxPane.lookup(cssID);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(node.translateXProperty(),600 - boxPane.getLayoutX(), Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}

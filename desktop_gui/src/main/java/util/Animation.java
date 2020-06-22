package util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

// Animation for notification
public class Animation {

    private final CustomStage stage;
    private final SequentialTransition sq;
    private volatile boolean trayIsShowing;

    public Animation(CustomStage stage) {
        this.stage = stage;
        sq = new SequentialTransition(setupShowAnimation(), setupDismissAnimation());
    }

    // pop out
    public Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();

        KeyValue kv1 = new KeyValue(stage.yLocationProperty(), stage.getY() + stage.getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.millis(1500), kv1);

        KeyValue kv2 = new KeyValue(stage.opacityProperty(), 0.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(1500), kv2);

        tl.getKeyFrames().addAll(kf1, kf2);

        tl.setOnFinished(e -> {
            trayIsShowing = false;
            stage.close();
            stage.setLocation(stage.getBottomRight());
        });

        return tl;
    }

    // pop in
    public Timeline setupShowAnimation() {
        Timeline tl = new Timeline();

        KeyValue kv1 = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY() + stage.getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);

        KeyValue kv2 = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY());
        KeyFrame kf2 = new KeyFrame(Duration.millis(700), kv2);

        KeyValue kv3 = new KeyValue(stage.opacityProperty(), 0.0);
        KeyFrame kf3 = new KeyFrame(Duration.ZERO, kv3);

        KeyValue kv4 = new KeyValue(stage.opacityProperty(), 1.0);
        KeyFrame kf4 = new KeyFrame(Duration.millis(1000), kv4);

        tl.getKeyFrames().addAll(kf1, kf2, kf3, kf4);

        tl.setOnFinished(e -> trayIsShowing = true);

        return tl;
    }


    public final void playSequential(Duration Delay) {
        sq.getChildren().get(1).setDelay(Delay);
        sq.play();
    }

    public final boolean isShowing() {
        return trayIsShowing;
    }

}

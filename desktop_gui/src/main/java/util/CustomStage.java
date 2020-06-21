package util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


// Custom stage for notification
public class CustomStage extends Stage {

    private Location bottomRight;

    public CustomStage(Region ap, StageStyle style) {
        initStyle(style);

        setSize(ap.getPrefWidth(), ap.getPrefHeight());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2;
        double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2;

        bottomRight = Location.at(x, y);
    }

    public Location getBottomRight() {
        return bottomRight;
    }

    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    public void setLocation(Location loc) {
        setX(loc.getX());
        setY(loc.getY());
    }

    private SimpleDoubleProperty xLocationProperty = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setX(newValue);
        }

        @Override
        public double get() {
            return getX();
        }
    };

    public SimpleDoubleProperty xLocationProperty() {
        return xLocationProperty;
    }

    private SimpleDoubleProperty yLocationProperty = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setY(newValue);
        }

        @Override
        public double get() {
            return getY();
        }
    };

    public SimpleDoubleProperty yLocationProperty() {
        return yLocationProperty;
    }

    final static class Location {

        private static Location at(double x, double y) {
            return new Location(x, y);
        }

        private final double x, y;

        private Location(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return String.format("(%f, %f)", x, y);
        }

        @Override
        public int hashCode() {
            return Double.valueOf(x).hashCode() ^ Double.valueOf(y).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            else if (!(obj instanceof Location)) return false;
            Location loc = (Location) obj;
            return x == loc.x && y == loc.y;
        }
    }
}
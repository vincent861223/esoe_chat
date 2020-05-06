module test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;

    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    // add icon pack modules
    requires org.kordamp.ikonli.feather;

    requires java.prefs;
    requires esoe.chat;

    opens login to javafx.fxml;
    exports login;
}
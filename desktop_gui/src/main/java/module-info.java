module desktop_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
    requires org.controlsfx.controls;

    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    // add icon pack modules
    requires org.kordamp.ikonli.feather;

    requires java.prefs;
    requires chat_module;

    // add every javafx package
    opens login to javafx.fxml;
    exports login;
    opens main to javafx.fxml;
    exports main;
    opens util to javafx.fxml;
    exports util;

    // In case of jfoenix has weird behavior(?)
//    opens com.sun.javafx.control.behavior to com.jfoenix;
//    exports com.sun.javafx.control.behavior to com.jfoenix;

}
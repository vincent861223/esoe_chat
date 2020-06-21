package login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import util.*;

public abstract class FormController extends CurrentUser {

    static StackPane boxPane;
    static PopOver popOver;

    public FormController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("popupBox.fxml"));
        Node root = loader.load();
        popOver = new PopOver(root);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        popOver.getRoot().getStylesheets().add(getClass().getResource("styles/popover.css").toExternalForm());
        popOver.setArrowSize(7.5);
        popOver.setArrowIndent(4.0);
    }

    static void setBoxPane(StackPane boxPane) {
        FormController.boxPane = boxPane;
    }

}

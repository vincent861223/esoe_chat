package login;

import client.ChatController;
import container.Response;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.PopOver;

import java.io.IOException;

public class FormController {

    static StackPane boxPane;
    static PopOver popOver;

    static ChatController chatController = new ChatController("127.0.0.1", 12345);
    static Response response;

    public FormController() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("popupBox.fxml"));
        Node root = loader.load();
        popOver = new PopOver(root);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        popOver.getRoot().getStylesheets().add(getClass().getResource("styles/popover.css").toExternalForm());
        popOver.setArrowSize(7.5);
        popOver.setArrowIndent(4.0);
    }

    void setBoxPane(StackPane boxPane) {
        FormController.boxPane = boxPane;
    }

}

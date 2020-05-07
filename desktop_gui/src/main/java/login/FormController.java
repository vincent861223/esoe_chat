package login;

import client.ChatController;
import container.Response;
import javafx.scene.layout.StackPane;

public class FormController {

    static StackPane boxPane;

    ChatController chatController = new ChatController("127.0.0.1", 12345);
    Response response;

    void setBoxPane(StackPane boxPane) {
        FormController.boxPane = boxPane;
    }

}

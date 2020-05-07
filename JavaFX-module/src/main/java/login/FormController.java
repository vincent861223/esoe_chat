package login;

import client.ChatController;
import container.Response;
import javafx.scene.layout.StackPane;

public class FormController {

    StackPane boxPane;

    ChatController chatController = new ChatController("127.0.0.1", 12345);
    Response response;

    public void setBoxPane(StackPane boxPane) {
        this.boxPane = boxPane;
    }

}

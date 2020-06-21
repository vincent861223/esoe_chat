package util;

import container.Message;
import container.MessageHistory;
import container.Response;
import javafx.util.Duration;
import main.ChatroomController;

import java.io.IOException;

public class UpdateHistoryThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (CurrentUser.chatController.updateHistoryChatroom != null) {

                Response response = CurrentUser.chatController.getHistory(CurrentUser.chatController.updateHistoryChatroom);

                // Load history in chatroom pane
                ChatroomController controller = Maps.chatroomControllers.get(
                        CurrentUser.chatController.updateHistoryChatroom);
                if (controller == null) {
                    try {
                        controller = Maps.loadPane(CurrentUser.chatController.updateHistoryChatroom);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                assert controller != null;
                controller.loadHistory();

                // Create a notification
                MessageHistory messageHistory = (MessageHistory) response.info;
                Message message = messageHistory.messages.get(messageHistory.messages.size() - 1);
                if (!message.senderID.equals(CurrentUser.chatController.userID)) {
                    String senderName = CurrentUser.chatController.getUsername(message.senderID).msg;
                    System.out.println(senderName);
                    NotificationUnit nu = new NotificationUnit(senderName, message.msg);
                    nu.show(Duration.seconds(2));
                }

                CurrentUser.chatController.updateHistoryChatroom = null;

            }
        }
    }
}

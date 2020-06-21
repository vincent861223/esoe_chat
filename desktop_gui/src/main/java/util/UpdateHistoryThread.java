package util;

import container.Message;
import container.MessageHistory;
import container.Response;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.ChatroomController;

import java.io.IOException;

public class UpdateHistoryThread extends Thread {


    @Override
    public void run() {
        while (true) {
            if (CUser.chatController.updateHistoryChatroom != null) {

                Response response = CUser.chatController.getHistory(CUser.chatController.updateHistoryChatroom);

                // Load history in chatroom pane
                ChatroomController controller = Maps.chatroomControllers.get(
                        CUser.chatController.updateHistoryChatroom);
                if (controller == null) {
                    try {
                        controller = Maps.loadPane(CUser.chatController.updateHistoryChatroom);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                assert controller != null;
                controller.loadHistory();

                // Create a notification
                if (CUser.userPrefs.getBoolean(CUser.NOTIFICATION_PREF, true)
                && Maps.notificationPrefs.get(CUser.chatController.updateHistoryChatroom)) {
                    MessageHistory messageHistory = (MessageHistory) response.info;
                    Message message = messageHistory.messages.get(messageHistory.messages.size() - 1);

                    // Send a notification when the new message sender is not myself
                    if (!message.senderID.equals(CUser.chatController.userID)) {
                        String senderName = CUser.chatController.getUsername(message.senderID).msg;

                        NotificationUnit nu = new NotificationUnit(senderName, message.msg);
                        nu.show(Duration.seconds(2));
                        // plays sound effect
                        MediaPlayer notificationSfx = new MediaPlayer(new Media(CUser.class.getResource("sfx/notification.mp3").toExternalForm()));
                        notificationSfx.play();
                    }
                }
                // reset for update
                CUser.chatController.updateHistoryChatroom = null;
            }
        }
    }
}

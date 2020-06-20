package util;

import client.ChatController;
import container.Message;
import container.MessageHistory;
import container.Response;

public class UpdateHistoryThread {

    private ChatController chatController;

    public UpdateHistoryThread(ChatController chatController) {
        this.chatController = chatController;
    }
    public void run() {
        while(true) {
            if(chatController.updateHistoryChatroom != null) {
                System.out.println(chatController.updateHistoryChatroom);
                Response response = chatController.getHistory(chatController.updateHistoryChatroom);
                MessageHistory messageHistory = (MessageHistory)response.info;
                for(Message message: messageHistory.messages) {
                    System.out.println(message.senderID + ":" + message.msg);
                }
                chatController.updateHistoryChatroom = null;
            }
        }
    }
}

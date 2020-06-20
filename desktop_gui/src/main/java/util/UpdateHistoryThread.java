package util;

import main.ChatroomController;

public class UpdateHistoryThread extends Thread {

    @Override
    public void run() {
        while(true) {
            if(CurrentUserInfo.chatController.updateHistoryChatroom != null) {
                ChatroomController controller = Maps.chatroomControllers.get(
                        CurrentUserInfo.chatController.updateHistoryChatroom);
                controller.loadHistory();
                CurrentUserInfo.chatController.updateHistoryChatroom = null;
            }
        }
    }
}

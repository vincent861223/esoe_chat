package client;

import container.ChatroomInfo;
import container.MessageInfo;
import container.Request;
import container.Response;

public class MessageAgent extends Agent {
	public MessageAgent(String ip, int port) {
		super(ip, port);
	}
	
	public Response createChatroom(String[] members) {
		ChatroomInfo chatroomInfo = new ChatroomInfo(members);
		Request request = new Request("NewChatroom", chatroomInfo);
		return sendRequest(request);
	}
	
	public Response sendMessage(ChatroomInfo chatroomInfo, String msg) {
		MessageInfo messageInfo = new MessageInfo(chatroomInfo.chatroomID, msg);
		Request request = new Request("SendMsg", messageInfo);
		return sendRequest(request);
	}
}

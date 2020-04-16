package client;

import container.ChatroomInfo;
import container.MessageInfo;
import container.Request;
import container.Response;

public class MessageAgent extends Agent {
	private String userID;
	public MessageAgent(String ip, int port, String userID) {
		super(ip, port);
		this.userID = userID;
	}
	
	public Response createChatroom(String[] members) {
		ChatroomInfo chatroomInfo = new ChatroomInfo(members);
		Request request = new Request("NewChatroom", chatroomInfo);
		return sendRequest(request);
	}
	
	public Response sendMessage(String chatroomID, String msg) {
		MessageInfo messageInfo = new MessageInfo(chatroomID, userID, msg);
		Request request = new Request("SendMsg", messageInfo);
		return sendRequest(request);
	}
}

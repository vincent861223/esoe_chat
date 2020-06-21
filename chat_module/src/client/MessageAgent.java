package client;


import container.ChatroomInfo;
import container.MessageInfo;
import container.Request;
import container.Response;
import container.GetHistoryInfo;;

public class MessageAgent extends Agent {
	private String userID;
	public MessageAgent(String ip, int port, String userID) {
		super(ip, port);
		this.userID = userID;
	}
	
	public Response createChatroom(String[] members) {
		String[] allMembers = new String[members.length+1];
		allMembers[0] = userID;
		int i = 1;
		for(String member: members) {
			allMembers[i] = member;
			i++;
		}
		ChatroomInfo chatroomInfo = new ChatroomInfo(allMembers);
		Request request = new Request("NewChatroom", chatroomInfo);
		return sendRequest(request);
	}
	
	public Response getChatroomList() {
		Request request = new Request("GetChatroomList", userID);
		return sendRequest(request);
	}
	
	public Response getChatroomName(String chatroomID) {
		Request request = new Request("GetChatroomName", chatroomID);
		return sendRequest(request);
	}
	
	public Response getChatroomMember(String chatroomID) {
		Request request = new Request("GetChatroomMember", chatroomID);
		return sendRequest(request);
	}
	
	public Response sendMessage(String chatroomID, String msg) {
		MessageInfo messageInfo = new MessageInfo(chatroomID, userID, msg);
		Request request = new Request("SendMsg", messageInfo);
		return sendRequest(request);
	}
	
	public Response getHistory(String chatroomID) {
		GetHistoryInfo getHistoryInfo = new GetHistoryInfo(chatroomID);
		Request request = new Request("GetHistory", getHistoryInfo);
		return sendRequest(request);
	}
}

package client;

import container.ChatroomInfo;
import container.Response;

public class ChatController {
	private Boolean loggedIn = false;
	private String serverIP;
	private int serverPort;
	private String username;
	private String password;
	private RegisterAgent registerAgent;
	private LoginAgent loginAgent;
	private MessageAgent messageAgent;
	
	public ChatController(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.registerAgent = new RegisterAgent(serverIP, serverPort);
		this.loginAgent = new LoginAgent(serverIP, serverPort);
		this.messageAgent = new MessageAgent(serverIP, serverPort);
	}
	
	public Response register(String username, String email, String password) {
		return registerAgent.register(username, email, password);
	}
	
	public Response login(String username, String password) {
		return loginAgent.login(username, password);
	}
	
	public Response creatChatroom(String[] members) {
		return messageAgent.createChatroom(members);
	}
	
	public Response sendMessage(ChatroomInfo chatroomInfo, String msg) {
		// Send a msg to a chatroom
		return messageAgent.sendMessage(chatroomInfo, msg);
	}
	
}

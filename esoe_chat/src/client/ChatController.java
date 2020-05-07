package client;

import container.Response;

public class ChatController {
	private String serverIP;
	private int serverPort;
	private String userID;
	private RegisterAgent registerAgent;
	private LoginAgent loginAgent;
	private MessageAgent messageAgent;
	private FriendAgent friendAgent;
	
	public ChatController(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.registerAgent = new RegisterAgent(serverIP, serverPort);
		this.loginAgent = new LoginAgent(serverIP, serverPort);
	}
	
	public Response register(String username, String email, String password) {
		return registerAgent.register(username, email, password);
	}
	
	public Response login(String username, String password) {
		Response response = loginAgent.login(username, password);
		if(response!= null && response.status.equals("OK")) this.userID = response.msg;
		return response;
	}
	
	public Response creatChatroom(String[] members) {
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.createChatroom(members);
	}
	
	public Response sendMessage(String chatroomID, String msg) {
		// Send a msg to a chatroom
		
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.sendMessage(chatroomID, msg);
	}
	
	public Response getHistory(String chatroomID) {
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.getHistory(chatroomID);
	}
	
	public Response addFriend(String friendID) {
		// Add userID as friend
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create friendAgent if the user has logged in.
		if(friendAgent == null) friendAgent = new FriendAgent(this.serverIP, this.serverPort, this.userID);
		return friendAgent.addFriend(friendID);
	}
	
	public Response getFriend() {
		// Add userID as friend
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create friendAgent if the user has logged in.
		if(friendAgent == null) friendAgent = new FriendAgent(this.serverIP, this.serverPort, this.userID);
		return friendAgent.getFriend();
	}
	
	public Response confirmFriend(String friendID) {
		// Add userID as friend
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create friendAgent if the user has logged in.
		if(friendAgent == null) friendAgent = new FriendAgent(this.serverIP, this.serverPort, this.userID);
		return friendAgent.confirmFriend(friendID);
	}
	
}

package client;

import java.util.Random;

import container.Response;
import container.SessionInfo;

public class ChatController {
	private String serverIP;
	private int serverPort;
	private int listenPort;
	private static final int listenPortMin = 10000;
	private static final int listenPortMax = 20000;
	public volatile String updateHistoryChatroom = null;
	public String userID;
	private String sessionID;
	private AccountAgent accountAgent;
	private MessageAgent messageAgent;
	private FriendAgent friendAgent;
	private ServerRequestHandler requestHandler;
	
	public ChatController(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.listenPort = new Random().nextInt((listenPortMax - listenPortMin) + 1) + listenPortMin;
		this.accountAgent = new AccountAgent(serverIP, serverPort);
		this.requestHandler = new ServerRequestHandler(listenPort, this);
		this.requestHandler.start();
	}
	
	public Response register(String username, String email, String password) {
		return accountAgent.register(username, email, password);
	}
	
	public Response login(String username, String password) {
		if(userID != null) return new Response("Failed", "Logged in already!");
		Response response = accountAgent.login(username, password, listenPort);
		if(response!= null && response.status.equals("OK")) {
			SessionInfo sessionInfo = (SessionInfo) response.info;
			this.userID = sessionInfo.userID;
			this.sessionID = sessionInfo.sessionID;
		}
		return response;
	}
	
	public Response logout() {
		Response response = accountAgent.logout(sessionID);
		if(response.status.equals("OK")) {
			userID = null;
			sessionID = null;
			messageAgent = null;
			friendAgent = null;
		}
		return response;
	}
	
	public Response getUsername(String userID) {
		Response response = accountAgent.getUsername(userID);
		return response;
	}
	
	public Response getUsername() {
		if(userID == null) return new Response("Failed", "Not logged in");
		return getUsername(userID);
	}
	
	public Response creatChatroom(String[] members) {
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.createChatroom(members);
	}
	
	public Response getChatroomList() {
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.getChatroomList();
	}
	
	public Response getChatroomName(String chatroomID) {
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.getChatroomName(chatroomID);
	}
	
	public Response getChatroomMember(String chatroomID) {
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create messageAgent if the user has logged in.
		if(messageAgent == null) this.messageAgent = new MessageAgent(this.serverIP, this.serverPort, this.userID);
		return messageAgent.getChatroomMember(chatroomID);
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
	
	public Response addFriend(String friendUsername) {
		// Add userID as friend
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create friendAgent if the user has logged in.
		if(friendAgent == null) friendAgent = new FriendAgent(this.serverIP, this.serverPort, this.userID);
		return friendAgent.addFriend(friendUsername);
	}
	
	public Response getFriend() {
		// Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create friendAgent if the user has logged in.
		if(friendAgent == null) friendAgent = new FriendAgent(this.serverIP, this.serverPort, this.userID);
		return friendAgent.getFriend();
	}
	
	public Response confirmFriend(String friendUsername) {
		// Add userID as friend
		//Failed if user has not logged in
		if(userID == null) return new Response("Failed", "Not logged in");
		// Create friendAgent if the user has logged in.
		if(friendAgent == null) friendAgent = new FriendAgent(this.serverIP, this.serverPort, this.userID);
		return friendAgent.confirmFriend(friendUsername);
	}
}

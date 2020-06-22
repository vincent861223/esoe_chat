package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import container.AddFriendInfo;
import container.ChatroomInfo;
import container.Friend;
import container.GetHistoryInfo;
import container.LoginInfo;
import container.MessageInfo;
import container.RegisterInfo;
import container.Request;
import container.Response;
import container.UserInfo;
import server.database.ChatDatabase;


public class RequestHandler extends Thread{
	private Socket clientSocket;
	
	public RequestHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void run() {
		ObjectInputStream in;
		ObjectOutputStream out;
		try {
			clientSocket.setSoTimeout(15000);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			// Get request from client
			Request request = (Request) in.readObject();
			String client_ip = clientSocket.getInetAddress().toString().replaceFirst("/", "");
			int client_port = clientSocket.getPort();
			System.out.println("[Client " + client_ip + ":" + client_port + "]: " + request);
			
			// Handle the request and send response to client
			Response response = handleRequest(request, client_ip, client_port);
			out.writeObject(response);
			
			in.close();
			out.close();
			clientSocket.close();
		}catch(Exception e){
			System.out.println("RequestHandler error: " + e.toString());
		}
	}
	public Response handleRequest(Request request, String client_ip, int client_port) {
		String current = System.getProperty("user.dir");
		ChatDatabase chatDatabase = new ChatDatabase(current + "/Database/chat.db", current + "/Database/init_table.sql");

		switch (request.command) {
			case "Register":
				return chatDatabase.addUser((RegisterInfo)request.info);
			case "Login":
				return chatDatabase.login((LoginInfo)request.info, client_ip, client_port);
			case "Logout":
				return chatDatabase.logout(request.userID);
			case "AddFriend":
				return chatDatabase.addFriend((AddFriendInfo)request.info);
			case "GetFriend":
				return chatDatabase.getFriend(((UserInfo)request.info));
			case "modifyFriend":
				return chatDatabase.modifyFriend(request.userID, (Friend)request.info);
			case "NewChatroom":
				return chatDatabase.newChatroom((ChatroomInfo)request.info);
			case "SendMsg":
				return chatDatabase.sendMessage((MessageInfo)request.info);
			case "GetHistory":
				return chatDatabase.getHistory((GetHistoryInfo)request.info);
			case "GetChatroomList":
				return chatDatabase.getChatroomList(request.userID);
			case "GetChatroomName":
				return chatDatabase.getChatroomName(request.userID);
			case "GetChatroomMember":
				return chatDatabase.getChatroomMember(request.userID);
			case "GetUsername":
				String username = chatDatabase.getUsername(request.userID);
				if(username != null) return new Response("OK", username);
				else return new Response("Failed", "user not found");
			case "GetUserID":
				String userID = chatDatabase.getUserID(request.userID);
				if(userID != null) return new Response("OK", userID);
				else return new Response("Failed", "user not found");
			default:
				return new Response("Unknown command");
		}
	}
	
}

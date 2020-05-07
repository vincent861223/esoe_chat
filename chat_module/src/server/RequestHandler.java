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
			System.out.println("[Client " + clientSocket.getInetAddress() + "]: " + request);
			
			// Handle the request and send response to client
			Response response = handleRequest(request);
			out.writeObject(response);
			
			in.close();
			out.close();
			clientSocket.close();
		}catch(Exception e){
			System.out.println("RequestHandler error: " + e.toString());
		}
	}
	
	public Response handleRequest(Request request) {
		ChatDatabase chatDatabase = new ChatDatabase("Database/chat.db", "Database/init_table.sql");
		switch (request.command) {
			case "Register":
				return chatDatabase.addUser((RegisterInfo)request.info);
			case "Login":
				return chatDatabase.login((LoginInfo)request.info);
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
			default:
				return new Response("Unknown command");
		}
	}
	
}

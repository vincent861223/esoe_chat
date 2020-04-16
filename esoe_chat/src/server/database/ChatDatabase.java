package server.database;

import java.util.HashMap;
import java.util.UUID;


import java.util.List;

import container.ChatroomInfo;
import container.LoginInfo;
import container.RegisterInfo;
import container.Response;
import container.MessageInfo;;


public class ChatDatabase extends Database {
	public ChatDatabase(String dbPath, String initSQLPath) {
		super(dbPath, initSQLPath);
	}
	
	public Response addUser(RegisterInfo registerInfo) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", UUID.randomUUID().toString());
		attr.put("username", registerInfo.username);
		attr.put("email", registerInfo.email);
		attr.put("password", hashPassword(registerInfo.password));
		Boolean success = insert("User", attr); // insert a new row into User table
		if(success) return new Response("OK", "");
		else return new Response("Failed", "User existed");
	}
	
	public Response login(LoginInfo loginInfo) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("username", loginInfo.username);
		attr.put("password", hashPassword(loginInfo.password));
		List<HashMap<String, String>> results = select("User", attr); // Check if the user exist and password match
		if(results.size() != 0) return new Response("OK", results.get(0).get("userID"));
		else return new Response("Failed", "Wrong password");
	}
	
	public Response newChatroom(ChatroomInfo chatroomInfo) {
		// Check member relationship
		// each member must be a friend of the establisher (the first member)
		
		return null;
	}
	
	public Response sendMessage(MessageInfo messageInfo) {
//		// Check if the sender is a friend of the receiver.
//		HashMap<String, String> attr = new HashMap<String, String>();
//		attr.put("userID", messageInfo.);
//		attr.put("friendID", messageInfo.receiver);
//		List<HashMap<String, String>> results = select("Friend", attr);
//		if(results.size() == 0 || results.get(0).get("pending").equals("1") || results.get(0).get("blocked").equals("1")) {
//			return new Response("Failed", "Not friend");
//		}
//		
//		// Access the chatroom
//		// Create a new chatroom if not exist
//		attr = new HashMap<String, String>();
//		
		return null;
	}
	
}

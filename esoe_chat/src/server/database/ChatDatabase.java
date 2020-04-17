package server.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


import java.util.List;

import container.AddFriendInfo;
import container.ChatroomInfo;
import container.Friend;
import container.FriendList;
import container.GetHistoryInfo;
import container.LoginInfo;
import container.Message;
import container.MessageHistory;
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
	
	public Response addFriend(AddFriendInfo addFriendInfo) {
		// Check if the user exist.
		if(!userExist(addFriendInfo.userID) || !userExist(addFriendInfo.friendID)) return new Response("Failed", "User not exist"); 
		// Check if they are friend already
		if(areFriend(addFriendInfo.userID, addFriendInfo.friendID)) return new Response("Failed", "Are friends already.");
		if(arePendingFriend(addFriendInfo.userID, addFriendInfo.friendID)) return new Response("Failed", "Already added, waiting for friend to confirm.");
		
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", addFriendInfo.userID);
		attr.put("friendID", addFriendInfo.friendID);
		attr.put("inviteSender", "1");
		attr.put("pending", "1");
		attr.put("blocked", "0");
		Boolean success = insert("Friend", attr);
		if(!success) return new Response("Failed", "Failed to insert new friend row");
		
		attr = new HashMap<String, String>();
		attr.put("userID", addFriendInfo.friendID);
		attr.put("friendID", addFriendInfo.userID);
		attr.put("inviteSender", "0");
		attr.put("pending", "1");
		attr.put("blocked", "0");
		success = insert("Friend", attr);
		if(!success) return new Response("Failed", "Failed to insert new friend row");
		
		return new Response("OK");
	}
	
	
	
	public Response newChatroom(ChatroomInfo chatroomInfo) {
		// Check member relationship
		// each member must be a friend of the establisher (the first member)
		for(int i= 1; i < chatroomInfo.members.length; i++) {
			if(!areFriend(chatroomInfo.members[0], chatroomInfo.members[i])) {
				return new Response("Failed", "Establisher is not a friend of " + chatroomInfo.members[i]);
			}
		}
		
		// Create a new chatroom
		String chatroomID = UUID.randomUUID().toString();
		for(String member : chatroomInfo.members) {
			HashMap<String, String> attr = new HashMap<String, String>();
			attr.put("chatroomID", chatroomID);
			attr.put("memberID", member);
			Boolean success = insert("Chatroom", attr);
			if(!success) return new Response("Failed", "Failed to add new Chatroom");
		}
		return new Response("OK", chatroomID);
	}
	
	public Response sendMessage(MessageInfo messageInfo) {
		// Check if the chatroom exist
		if(!chatroomExist(messageInfo.chatroomID)) return new Response("Failed", "Chatroom does not exist.");
		// insert the message into Message table
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("messageID", UUID.randomUUID().toString());
		attr.put("chatroomID", messageInfo.chatroomID);
		attr.put("message", messageInfo.message);
		attr.put("sender", messageInfo.senderID);
		attr.put("timestamp", getTimestamp());
		Boolean success = insert("Message", attr);
		if(success) return new Response("OK");
		else return new Response("Failed", "DB send message error");
	}
	
	public Response getFriend(String userID) {
		if(!userExist(userID)) return new Response("Failed", "User does not exist");
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", userID);
		List<HashMap<String, String>> results = select("Friend", attr);
		FriendList friendList = new FriendList();
		for(HashMap<String, String> result: results) {
			friendList.friends.add(new Friend(result.get("friendID"), result.get("pending"), result.get("blocked")));
		}
		return new Response("OK", friendList);
	}
	
	public Response getHistory(GetHistoryInfo getHistoryInfo) {
		// Check if the chatroom exist
		if(!chatroomExist(getHistoryInfo.chatroomID)) return new Response("Failed", "Chatroom does not exist.");
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("chatroomID = ", getHistoryInfo.chatroomID);
		attr.put("timestamp > ", getHistoryInfo.startTime);
		List<String> sortAttr = new ArrayList<String>();
		sortAttr.add("timestamp");
		List<HashMap<String, String>> results = selectCondition("Message", attr, sortAttr);
		List<Message> messages = new ArrayList<Message>();
		for(HashMap<String, String> result: results) {
			messages.add(new Message(result.get("sender"), result.get("message"), result.get("timestamp")));
		}
		MessageHistory messageHistory = new MessageHistory(messages);
		return new Response("OK", messageHistory);
	}
	
	private Boolean areFriend(String userA, String userB) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", userA);
		attr.put("friendID", userB);
		List<HashMap<String, String>> results = select("Friend", attr);
		// not friend if no entry found or the relationship is pending.
		if(results.size() == 0 || results.get(0).get("pending").equals("1")) return false;
		else return true;
	}
	
	private Boolean arePendingFriend(String userA, String userB) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", userA);
		attr.put("friendID", userB);
		List<HashMap<String, String>> results = select("Friend", attr);
		// not friend if no entry found or the relationship is pending.
		if(results.size() == 0 || results.get(0).get("pending").equals("0")) return false;
		else return true;
	}
	
	private Boolean userExist(String userID) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", userID);
		List<HashMap<String, String>> results = select("User", attr); // Check if the user exist
		if(results.size() == 0) return false;
		else return true;
	}
	
	private Boolean chatroomExist(String chatroomID) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("chatroomID", chatroomID);
		List<HashMap<String, String>> results = select("Chatroom", attr); // Check if the user exist
		if(results.size() == 0) return false;
		else return true;
	}
	
}

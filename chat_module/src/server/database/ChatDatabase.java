package server.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import client.AgentThread;

import java.util.List;

import container.AddFriendInfo;
import container.ChatroomInfo;
import container.ChatroomList;
import container.Friend;
import container.FriendList;
import container.GetHistoryInfo;
import container.LoginInfo;
import container.Message;
import container.MessageHistory;
import container.RegisterInfo;
import container.Request;
import container.Response;
import container.SessionInfo;
import container.UpdateHistoryInfo;
import container.UserInfo;
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
	
	public Response login(LoginInfo loginInfo, String client_ip, int client_port) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("username", loginInfo.username);
		attr.put("password", hashPassword(loginInfo.password));
		List<HashMap<String, String>> results = select("User", attr); // Check if the user exist and password match
		if(results.size() != 0) {
			attr = new HashMap<String, String>();
			String sessionID = UUID.randomUUID().toString();
			attr.put("userID", results.get(0).get("userID"));
			attr.put("ip", client_ip);
			attr.put("port", String.valueOf(loginInfo.listenPort));
			attr.put("sessionID", sessionID);
			insert("Session", attr);
			return new Response("OK", new SessionInfo(results.get(0).get("userID"), sessionID));
		}
		else return new Response("Failed", "Wrong password");
	}
	
	public Response logout(String sessionID) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("sessionID", sessionID);
		if(delete("Session", attr)) return new Response("OK");
		else return new Response("Failed", "Unable to delete session");
	}
	
	public Response addFriend(AddFriendInfo addFriendInfo) {
		String friendID = getUserID(addFriendInfo.friendUsername);
		if(friendID == null) return new Response("Failed", "friend username does not exist");
		// Check if the user exist.
		if(!userExist(addFriendInfo.userID) || !userExist(friendID)) return new Response("Failed", "User not exist"); 
		// Check if they are friend already
		if(areFriend(addFriendInfo.userID, friendID)) return new Response("Failed", "Are friends already.");
		if(arePendingFriend(addFriendInfo.userID, friendID)) return new Response("Failed", "Already added, waiting for friend to confirm.");
		
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", addFriendInfo.userID);
		attr.put("friendID", friendID);
		attr.put("inviteSender", "1");
		attr.put("pending", "1");
		attr.put("blocked", "0");
		Boolean success = insert("Friend", attr);
		if(!success) return new Response("Failed", "Failed to insert new friend row");
		
		attr = new HashMap<String, String>();
		attr.put("userID", friendID);
		attr.put("friendID", addFriendInfo.userID);
		attr.put("inviteSender", "0");
		attr.put("pending", "1");
		attr.put("blocked", "0");
		success = insert("Friend", attr);
		if(!success) return new Response("Failed", "Failed to insert new friend row");
		
		return new Response("OK");
	}
	
	public Response modifyFriend(String userID, Friend friend) {
		// Check if the user exist.
		String friendUserID = getUserID(friend.friendUsername);
		if(friendUserID == null || !userExist(userID) || !userExist(friendUserID)) return new Response("Failed", "User not exist"); 
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("pending", friend.pending == true? "1" : "0");
		attr.put("blocked", friend.blocked == true? "1" : "0");
		HashMap<String, String> cond_attr = new HashMap<String, String>();
		cond_attr.put("userID", userID);
		cond_attr.put("friendID", friendUserID);
		Boolean success = update("Friend", attr, cond_attr);
		if(!success) return new Response("Failed", "Failed to modify the friend entry");
		
		attr = new HashMap<String, String>();
		attr.put("pending", friend.pending == true? "1" : "0");
		attr.put("blocked", friend.blocked == true? "1" : "0");
		cond_attr = new HashMap<String, String>();
		cond_attr.put("userID", friendUserID);
		cond_attr.put("friendID", userID);
		success = update("Friend", attr, cond_attr);
		if(!success) return new Response("Failed", "Failed to modify the friend entry");
		else return new Response("OK");
	}
	
	
	
	public Response newChatroom(ChatroomInfo chatroomInfo) {
		// Check member relationship
		// each member must be a friend of the establisher (the first member)
		String[] members = new String[chatroomInfo.members.length];
		members[0] = chatroomInfo.members[0];
		for(int i = 1; i < chatroomInfo.members.length; i++) {
			members[i] = getUserID(chatroomInfo.members[i]);
			if(members[i] == null) return new Response("Failed", chatroomInfo.members[i] + " does not exist.");
		}
		for(int i= 1; i < members.length; i++) {
			if(!areFriend(members[0], members[i])) {
				return new Response("Failed", "Establisher is not a friend of " + chatroomInfo.members[i]);
			}
		}
		
		// Create a new chatroom
		String chatroomID = UUID.randomUUID().toString();
		for(String member : members) {
			HashMap<String, String> attr = new HashMap<String, String>();
			attr.put("chatroomID", chatroomID);
			attr.put("memberID", member);
			Boolean success = insert("Chatroom", attr);
			if(!success) return new Response("Failed", "Failed to add new Chatroom");
		}
		return new Response("OK", chatroomID);
	}
	
	public Response getChatroomList(String userID) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("memberID", userID);
		List<HashMap<String, String>> results = select("Chatroom", attr);
		String[] chatroomIDs = new String[results.size()];
		for(int i = 0; i < results.size(); i++) {
			chatroomIDs[i] = results.get(i).get("chatroomID");
		}
		return new Response("OK", new ChatroomList(chatroomIDs));
	}
	
	public Response getChatroomName(String chatroomID) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("chatroomID", chatroomID);
		List<HashMap<String, String>> results = select("Chatroom", attr);
		if(results.size() == 0) return new Response("Failed", "No such chatroom");
		else{
			String chatroomName = "";
			for(HashMap<String, String> result: results) {
				String username = getUserName(result.get("memberID"));
				chatroomName += username + " ";
			}
			chatroomName = chatroomName.strip();
			return new Response("OK", chatroomName);
		}
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
		if(success) {
			// alert users online in this chatroom to update chat history.
			Request request = new Request("UpdateHistory", new UpdateHistoryInfo(messageInfo.chatroomID));
			attr = new HashMap<String, String>();
			attr.put("chatroomID", messageInfo.chatroomID);
			List<HashMap<String, String>> results = select("Chatroom", attr);
			for(HashMap<String, String> result : results) {
				HashMap<String, String> attr_ses = new HashMap<String, String>();
				attr_ses.put("userID", result.get("memberID"));
				List<HashMap<String, String>> results_ses = select("Session", attr_ses);
				for(HashMap<String, String> result_ses: results_ses) {
					// Send update message to all user in the chatroom
					AgentThread agentThread = new AgentThread(result_ses.get("ip"), Integer.parseInt(result_ses.get("port")), request, 500);
					agentThread.start();
				}
				
			}
			return new Response("OK");
		}
		else return new Response("Failed", "DB send message error");
	}
	
	public Response getFriend(UserInfo userInfo) {
		if(!userExist(userInfo.userID)) return new Response("Failed", "User does not exist");
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", userInfo.userID);
		List<HashMap<String, String>> results = select("Friend", attr);
		FriendList friendList = new FriendList();
		for(HashMap<String, String> result: results) {
			System.out.println(getUserName(result.get("friendID")));
			friendList.friends.add(new Friend(getUserName(result.get("friendID")), result.get("pending"), result.get("blocked"), result.get("inviteSender")));
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
	
	private String getUserID(String username) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("username", username);
		List<HashMap<String, String>> results = select("User", attr);
		if(results.size() == 0) return null;
		else return results.get(0).get("userID");
	}
	
	private String getUserName(String userID) {
		HashMap<String, String> attr = new HashMap<String, String>();
		attr.put("userID", userID);
		List<HashMap<String, String>> results = select("User", attr);
		if(results.size() == 0) return null;
		else return results.get(0).get("username");
	}
}

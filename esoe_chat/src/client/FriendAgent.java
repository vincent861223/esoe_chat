package client;

import container.AddFriendInfo;
import container.Request;
import container.Response;

public class FriendAgent extends Agent {
	String userID;
	
	public FriendAgent(String ip, int port, String userID) {
		super(ip, port);
		this.userID = userID;
	}
	
	public Response addFriend(String friendID) {
		AddFriendInfo addFriendInfo = new AddFriendInfo(userID, friendID);
		Request request = new Request("AddFriend", addFriendInfo);
		return sendRequest(request);
	}
}

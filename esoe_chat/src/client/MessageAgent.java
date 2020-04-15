package client;

import container.MessageInfo;
import container.Request;
import container.Response;

public class MessageAgent extends Agent {
	private String username;
	public MessageAgent(String ip, int port) {
		super(ip, port);
	}
	
	public boolean sendMessage(String receiver, String msg) {
		MessageInfo messageInfo = new MessageInfo(this.username, receiver, msg);
		Request request = new Request("SendMsg", messageInfo);
		Response response = sendRequest(request);
		return response.status.equals("OK");
	}
}

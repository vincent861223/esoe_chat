package client;

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
	
	public Boolean register(String username, String password, String email) {
		return registerAgent.register(username, email, password);
	}
	
	public Boolean login(String username, String password) {
		return loginAgent.login(username, password);
	}
	
	public Boolean sendMessage(String receiver, String msg) {
		return messageAgent.sendMessage(receiver, msg);
	}
	
}

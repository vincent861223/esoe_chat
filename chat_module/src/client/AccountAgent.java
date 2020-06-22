package client;

import container.LoginInfo;
import container.RegisterInfo;
import container.Request;
import container.Response;

public class AccountAgent extends Agent {
	// The agent for doing Account related operations
	public AccountAgent(String ip, int port) {
		super(ip, port);
	}
	
	public Response register(String username, String email, String password) {
		// Send request to server to register a new account
		RegisterInfo registerInfo = new RegisterInfo(username, email, password);
		Request request = new Request("Register", registerInfo);
		return sendRequest(request);
	}
	
	public Response login(String username, String password, int listenPort) {
		// Send request to server to login
		LoginInfo loginInfo = new LoginInfo(username, password, listenPort);
		Request request = new Request("Login", loginInfo);
		return sendRequest(request);
	}
	
	public Response logout(String sessionID) {
		// Send request to server to logout 
		Request request = new Request("Logout", sessionID);
		return sendRequest(request);
	}
	
	public Response getUsername(String userID) {
		// Send request to get username by userID
		Request request = new Request("GetUsername", userID);
		return sendRequest(request);
	}
	
	public Response getUserID(String username) {
		// Send request to get userID by username
		Request request = new Request("GetUserID", username);
		return sendRequest(request);
	}
}

package client;

import container.LoginInfo;
import container.RegisterInfo;
import container.Request;
import container.Response;

public class AccountAgent extends Agent {
	public AccountAgent(String ip, int port) {
		super(ip, port);
	}
	
	public Response register(String username, String email, String password) {
		RegisterInfo registerInfo = new RegisterInfo(username, email, password);
		Request request = new Request("Register", registerInfo);
		return sendRequest(request);
	}
	
	public Response login(String username, String password, int listenPort) {
		LoginInfo loginInfo = new LoginInfo(username, password, listenPort);
		Request request = new Request("Login", loginInfo);
		return sendRequest(request);
	}
	
	public Response logout(String sessionID) {
		Request request = new Request("Logout", sessionID);
		return sendRequest(request);
	}
	
	public Response getUsername(String userID) {
		Request request = new Request("GetUsername", userID);
		return sendRequest(request);
	}
	
	public Response getUserID(String username) {
		Request request = new Request("GetUserID", username);
		return sendRequest(request);
	}
}

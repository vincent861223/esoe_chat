package client;

import container.LoginInfo;
import container.Request;
import container.Response;

public class LoginAgent extends Agent {
	public LoginAgent(String ip, int port) {
		super(ip, port);
	}
	public Response login(String username, String password, int listenPort) {
		LoginInfo loginInfo = new LoginInfo(username, password, listenPort);
		Request request = new Request("Login", loginInfo);
		return sendRequest(request);
	}
}

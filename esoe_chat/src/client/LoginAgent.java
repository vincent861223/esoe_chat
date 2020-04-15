package client;

import container.LoginInfo;
import container.Request;
import container.Response;

public class LoginAgent extends Agent {
	public LoginAgent(String ip, int port) {
		super(ip, port);
	}
	public Boolean login(String username, String password) {
		LoginInfo loginInfo = new LoginInfo(username, password);
		Request request = new Request("login", loginInfo);
		Response response = sendRequest(request);
		return response.status.equals("OK");
	}
}

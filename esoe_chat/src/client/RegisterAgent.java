package client;

import container.RegisterInfo;
import container.Request;
import container.Response;

public class RegisterAgent extends Agent {
	public RegisterAgent(String ip, int port) {
		super(ip, port);
	}
	
	public Boolean register(String username, String email, String password) {
		RegisterInfo registerInfo = new RegisterInfo(username, email, password);
		Request request = new Request("Register", registerInfo);
		Response response = sendRequest(request);
		return response.status.equals("OK");
	}
}

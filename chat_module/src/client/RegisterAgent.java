package client;

import container.RegisterInfo;
import container.Request;
import container.Response;

public class RegisterAgent extends Agent {
	public RegisterAgent(String ip, int port) {
		super(ip, port);
	}
	
	public Response register(String username, String email, String password) {
		RegisterInfo registerInfo = new RegisterInfo(username, email, password);
		Request request = new Request("Register", registerInfo);
		return sendRequest(request);
	}
}
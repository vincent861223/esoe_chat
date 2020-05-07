package client;

import java.net.Socket;

import container.Request;
import container.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Agent {
	protected String ip;
	protected int port;
	
	public Agent() {
		this("127.0.0.1", 12345);
	}

	public Agent(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public Response sendRequest(Request request) {
		try {
			Socket server = new Socket(this.ip, this.port);
			server.setSoTimeout(15000);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			
			out.writeObject(request); // Send the request to server.
			
			Response response = (Response) in.readObject(); // Receive response from server
			
			out.close();
			in.close();
			server.close();
			return response;
		} catch (Exception e) {
			System.out.println("[Client] Socket connection error !: " + e.toString());
			return null;
		}
	}

}

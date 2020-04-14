package chat_client;

import java.net.Socket;
import chat.Request;
import chat.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {
	private String ip;
	private int port;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public Response sendRequest(Request request) {
		try {
			Socket server = new Socket(this.ip, this.port);
			server.setSoTimeout(15000);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			
			out.writeObject(request);
			
			Response response = (Response) in.readObject();
			
			out.close();
			in.close();
			server.close();
			return response;
		} catch (Exception e) {
			System.out.println("[Client] Socket connection error !: " + e.toString());
			return null;
		}
	}

	public static void main(String args[]) {
		Client client = new Client("127.0.0.1", 12345);
		Request request = new Request("SendMsg", "Hello", "A", "B");
		System.out.println("Request: " + request);
		Response response = client.sendRequest(request);
		System.out.println("Response: " + response);
	}
}

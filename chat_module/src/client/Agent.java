package client;

import java.net.Socket;

import container.Request;
import container.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Agent extends Thread{
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
		return sendRequest(request, 15000);
	}
	
	public Response sendRequest(Request request, int timeout) {
		Socket server;
		ObjectOutputStream out;
		ObjectInputStream in;
		try {
			server = new Socket(this.ip, this.port);
			server.setSoTimeout(timeout);
			
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			
			out.writeObject(request); // Send the request to server.
			out.flush();
			
			Response response = (Response) in.readObject(); // Receive response from server
			
			out.close();
			in.close();
			server.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Socket connection error !: " + e.toString());
			return null;
		}
	}
	
	public void run(Request request, int timeout) {
		Socket server;
		ObjectOutputStream out;
		try {
			server = new Socket(this.ip, this.port);
			server.setSoTimeout(timeout);
			
			out = new ObjectOutputStream(server.getOutputStream());
			
			out.writeObject(request); 
			out.flush();

			
			out.close();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Socket connection error !: " + e.toString());
		}
	}

}

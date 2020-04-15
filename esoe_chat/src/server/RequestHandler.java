package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import container.Request;
import container.Response;


public class RequestHandler extends Thread{
	private Socket clientSocket;
	
	public RequestHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void run() {
		ObjectInputStream in;
		ObjectOutputStream out;
		try {
			clientSocket.setSoTimeout(15000);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			// Get request from client
			Request request = (Request) in.readObject();
			System.out.println("[Client " + clientSocket.getInetAddress() + "]: " + request);
			
			// Handle the request and send response to client
			Response response = handleRequest(request);
			out.writeObject(response);
			
			in.close();
			out.close();
			clientSocket.close();
		}catch(Exception e){
			System.out.println("RequestHandler error: " + e.toString());
		}
	}
	
	public Response handleRequest(Request request) {
		switch (request.command) {
			case "Register":
				break;
			case "Login":
				break;
			case "SendMsg":
				break;
			default:
				break;
		}
		return new Response("OK");
	}
	
}

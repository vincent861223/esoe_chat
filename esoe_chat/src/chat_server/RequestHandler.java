package chat_server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import chat.Request;
import chat.Response;


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
			
			Request request = (Request) in.readObject();
			System.out.println("[Client " + clientSocket.getInetAddress() + "]: " + request);
			
			Response response = new Response("OK");
			out.writeObject(response);
			in.close();
			out.close();
			clientSocket.close();
		}catch(Exception e){
			System.out.println("RequestHandler error: " + e.toString());
		}
	}
	
}

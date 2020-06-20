package client;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import container.Request;
import container.Response;
import container.UpdateHistoryInfo;

public class ServerRequestHandler extends Thread{
	private ServerSocket server;
	private int port;
	private ChatController chatController;
	
	public ServerRequestHandler(int port, ChatController chatController) {
		this.port = port;
		this.chatController = chatController;
		System.out.println("[Client] Starting requestHandler at port:" + port);
		try {
			server = new ServerSocket(this.port);
		}catch(java.io.IOException e) {
			System.out.println("Server creation error: " + e.toString());
		}
	}
	
	public void run() {
		Socket clientSocket;
		while(true) {
			// Loop for accepting new client
			clientSocket = null;
			try {
				synchronized(server) {
					clientSocket = server.accept();
					// When a new client is accepted, create a new thread (RequestHandler) to handle the client request.
					//(new RequestHandler(clientSocket)).start();
					ObjectInputStream in;
					//ObjectOutputStream out;
					try {
						clientSocket.setSoTimeout(15000);
						//out = new ObjectOutputStream(clientSocket.getOutputStream());
						in = new ObjectInputStream(clientSocket.getInputStream());
						
						// Get request from client
						Request request = (Request) in.readObject();
						//System.out.println("[ClientRequestHandler " + clientSocket.getInetAddress() + "]: " + request);
						
						// Handle the request and send response to client
						Response response = handleRequest(request);
						//out.writeObject(response);
						
						in.close();
						//out.close();
						clientSocket.close();
					}catch(Exception e){
						System.out.println("RequestHandler error: " + e.toString());
					}
				}
				
			}catch(java.io.IOException e){
				System.out.println("Connection error: " + e.toString());
				continue;
			}
		}
	}

	public Response handleRequest(Request request) {
		switch (request.command) {
			case "UpdateHistory":
				//System.out.println(request);
				chatController.updateHistoryChatroom = ((UpdateHistoryInfo)request.info).chatroomID;
				return new Response("OK");
			default:
				return new Response("InvalidCMD");
		}
		
		
	}
}

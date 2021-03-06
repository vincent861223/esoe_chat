package server;

import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread{
	// The entry point of Server. 
	// This class will open a listen port and accept client when the client is connected.
	// When a client is accepted, a new Request handler thread will be spawned to handle the client request.
	private ServerSocket server;
	private int port;
	
	public Server(int port) {
		this.port = port;
		System.out.println("[Server] Starting server at port:" + port);
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
					(new RequestHandler(clientSocket)).start();
				}
				
			}catch(java.io.IOException e){
				System.out.println("Connection error: " + e.toString());
				continue;
			}
		}
	}
	
	public static void main(String[] argv) {
		Server server = new Server(12345);
		server.start();		
		
	}
	
}

package client;

import java.net.Socket;

import container.Request;


import java.io.ObjectOutputStream;

public class AgentThread extends Thread{
	protected String ip;
	protected int port;
	protected Request request;
	protected int timeout;
	
	public AgentThread() {
		this("127.0.0.1", 12345, null, 1000);
	}

	public AgentThread(String ip, int port, Request request, int timeout) {
		this.ip = ip;
		this.port = port;
		this.request = request;
		this.timeout = timeout;
	}
	
	
	
	public void run() {
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

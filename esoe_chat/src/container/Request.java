package container;

import java.io.Serializable;


public class Request extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3467064688812671749L;
	public String command;
	public Container info;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	
	public Request(String command, Container info) {
		this.command = command;
		this.info = info;
	}
	
	public Container getInfo() {
		return info;
	}

	public void setInfo(Container info) {
		this.info = info;
	}
	
}

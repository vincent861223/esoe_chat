package container;

import java.io.Serializable;


public class Response extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4115674824486048225L;
	public String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public Response(String status) {
		this.status = status;
	}
	
}

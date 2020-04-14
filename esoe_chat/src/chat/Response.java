package chat;

import java.io.Serializable;

import org.json.JSONObject;

public class Response implements Serializable{
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
	
	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}

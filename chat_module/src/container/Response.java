package container;

import java.io.Serializable;


public class Response extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4115674824486048225L;
	public String status;
	public Container info;
	public String msg;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Container getInfo() {
		return info;
	}

	public void setInfo(Container info) {
		this.info = info;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Response(String status) {
		this.status = status;
		this.msg = "";
	}
	
	public Response(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public Response(String status, Container info) {
		this.status = status;
		this.info = info;
	}
	
}
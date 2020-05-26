package container;

import java.io.Serializable;


public class LoginInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -987148438774747439L;
	public String username;
	public String password;
	public int listenPort;
	public int getListenPort() {
		return listenPort;
	}
	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginInfo() {
		
	}
	
	public LoginInfo(String username, String password, int listenPort) {
		this.username = username;
		this.password = password;
		this.listenPort = listenPort;
	}
}

package container;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;


public class LoginInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -987148438774747439L;
	@SerializedName("username")
	public String username;
	
	@SerializedName("password")
	public String password;
	
	@SerializedName("listenPort")
	public int listenPort;
	
	public LoginInfo(String username, String password, int listenPort) {
		this.username = username;
		this.password = password;
		this.listenPort = listenPort;
	}
}

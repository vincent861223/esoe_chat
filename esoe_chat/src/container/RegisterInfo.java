package container;

import java.io.Serializable;

public class RegisterInfo extends Container implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7571184776417282083L;
	public String username;
	public String email;
	public String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public RegisterInfo() {
		
	}
	
	public RegisterInfo(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
}

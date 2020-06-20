package container;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserInfo extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3455185393392627798L;
	@SerializedName("userID")
	public String userID;
	
	public UserInfo(String userID) {
		this.userID = userID;
	}
}

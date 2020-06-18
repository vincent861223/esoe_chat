package container;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UpdateHistoryInfo extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("chatroomID")
	public String chatroomID;
	
	public UpdateHistoryInfo(String chatroomID) {
		this.chatroomID = chatroomID;
	}
}

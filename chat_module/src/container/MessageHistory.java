package container;

import java.io.Serializable;
import java.util.List;

public class MessageHistory extends Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5274934768414413528L;
	public List<Message> messages;
	
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public MessageHistory(List<Message> messages) {
		this.messages = messages;
	}
}

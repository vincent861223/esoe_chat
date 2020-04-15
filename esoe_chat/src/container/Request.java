package container;

import java.io.Serializable;


public class Request extends Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3467064688812671749L;
	public String command;
	public RegisterInfo registerInfo;
	public LoginInfo loginInfo;
	public MessageInfo messageInfo;
	
	
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public RegisterInfo getRegisterInfo() {
		return registerInfo;
	}

	public void setRegisterInfo(RegisterInfo registerInfo) {
		this.registerInfo = registerInfo;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(MessageInfo messageInfo) {
		this.messageInfo = messageInfo;
	}

	public Request(String command, RegisterInfo registerInfo, LoginInfo loginInfo, MessageInfo messageInfo) {
		this.command = command;
		this.registerInfo = registerInfo;
		this.loginInfo = loginInfo;
		this.messageInfo = messageInfo;
	}
	
	public Request(String command, RegisterInfo registerInfo) {
		this(command, registerInfo, null, null);
	}
	
	public Request(String command, LoginInfo loginInfo) {
		this(command, null, loginInfo, null);
	}
	
	public Request(String command, MessageInfo messageInfo) {
		this(command, null, null, messageInfo);
	}
	
}

package gui;

public class LoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6389457110763794368L;
	private final String errMessage;

	public LoginException(String errMessage) {
		super();
		this.errMessage = errMessage;
	}
	
	public String getErrMessage() {
		return errMessage;
	}
	
}

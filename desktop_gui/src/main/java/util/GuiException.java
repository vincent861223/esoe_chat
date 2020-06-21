package util;

public class GuiException extends Exception {

	private static final long serialVersionUID = 6389457110763794368L;
	private final String errMessage;

	public GuiException(String errMessage) {
		super();
		this.errMessage = errMessage;
	}
	
	public String getErrMessage() {
		return errMessage;
	}
	
}

package client;


public class Main {
	public static void main(String[] argv) {
		ChatController chatController = new ChatController("127.0.0.1", 12345);
		Boolean result;
		result = chatController.register("Vincent", "aaa@bbb.com", "123");
		System.out.println(result);
		result = chatController.login("Vincent", "123");
		System.out.println(result);
		result = chatController.sendMessage("Kevin", "Hello");
		System.out.println(result);
	}
}

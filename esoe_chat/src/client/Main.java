package client;


import container.Response;

public class Main {
	public static void main(String[] argv) {
		ChatController chatController = new ChatController("127.0.0.1", 12345);
		Response response;
		response = chatController.register("Vincent", "aaa@bbb.com", "123");
		System.out.println(response);
		response = chatController.login("Vincent", "123");
		System.out.println(response);
		response = chatController.login("Vincent", "1234");
		System.out.println(response);
		response = chatController.creatChatroom(new String[]{"Alice", "Bob"});
		System.out.println(response);
//		response = chatController.sendMessage("Kevin", "Hello");
//		System.out.println(response);
	}
}

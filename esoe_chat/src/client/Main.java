package client;


import container.Response;

public class Main {
	public static void main(String[] argv) {
		ChatController chatController = new ChatController("127.0.0.1", 12345);
		Response response;
		response = chatController.register("Vincent", "aaa@bbb.com", "123");
		System.out.println(response);
		response = chatController.login("Vincent", "1234");
		System.out.println(response);
		response = chatController.login("Vincent", "123");
		System.out.println(response);
		response = chatController.creatChatroom(new String[]{"Vincent", "Alice", "Bob"});
		System.out.println(response);
		String chatroomID = response.msg;
		response = chatController.sendMessage(chatroomID, "Hello");
		System.out.println(response);
		response = chatController.sendMessage(chatroomID, "I am Vincent");
		System.out.println(response);
		response = chatController.getHistory(chatroomID);
		System.out.println(response);
		
	}
}

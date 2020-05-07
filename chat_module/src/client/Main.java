package client;


import container.Response;

public class Main {
	public static void main(String[] argv) {
		ChatController chatController = new ChatController("127.0.0.1", 12345);
		Response response;
		response = chatController.register("Alice", "Alice@gmail.com", "123");
		System.out.println(response);
		response = chatController.register("Bob", "Bob@gmail.com", "123");
		System.out.println(response);
		response = chatController.register("Carol", "Carol@gmail.com", "123");
		System.out.println(response);
		response = chatController.register("Vincent", "Vincent@gmail.com", "123");
		System.out.println(response);
		response = chatController.login("Vincent", "1234");
		System.out.println(response);
		response = chatController.login("Vincent", "123");
		System.out.println(response);
		response = chatController.addFriend("ce9443fb-f487-4f04-82bd-75282b59cac5");
		System.out.println(response);
		response = chatController.addFriend("3287832e-937c-46c5-849b-5f4bd2bdfde1");
		System.out.println(response);
		response = chatController.addFriend("3aceada5-97c3-469d-8995-e512f2e1ee77");
		System.out.println(response);
		response = chatController.getFriend();
		System.out.println(response);
		response = chatController.confirmFriend("ce9443fb-f487-4f04-82bd-75282b59cac5");
		System.out.println(response);
		response = chatController.confirmFriend("3287832e-937c-46c5-849b-5f4bd2bdfde1");
		System.out.println(response);
		response = chatController.confirmFriend("3aceada5-97c3-469d-8995-e512f2e1ee77");
		System.out.println(response);
		response = chatController.getFriend();
		System.out.println(response);
		response = chatController.creatChatroom(new String[]{"ce9443fb-f487-4f04-82bd-75282b59cac5", "3287832e-937c-46c5-849b-5f4bd2bdfde1", "3aceada5-97c3-469d-8995-e512f2e1ee77"});
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

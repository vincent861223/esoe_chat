package client;


import java.util.Scanner;

import container.ChatroomList;
import container.MessageHistory;
import container.Response;
import container.Message;;

public class Main{
	
	public static void main(String[] argv) {
		ChatController chatController = new ChatController("127.0.0.1", 12345, 11113);
		TestUpdateHistoryThread testUpdateHistoryThread = new TestUpdateHistoryThread(chatController);
		testUpdateHistoryThread.start();
		Scanner scanner = new Scanner(System.in);
		
		String chatroomID;
		Response response;
//		response = chatController.register("Alice", "Alice@gmail.com", "123");
//		System.out.println(response);
//		response = chatController.register("Bob", "Bob@gmail.com", "123");
//		System.out.println(response);
//		response = chatController.register("Carol", "Carol@gmail.com", "123");
//		System.out.println(response);
//		response = chatController.register("Vincent", "Vincent@gmail.com", "123");
//		System.out.println(response);
//		response = chatController.login("Vincent", "1234");
//		System.out.println(response);
//		response = chatController.login("Vincent", "123");
//		System.out.println(response);
//
//		response = chatController.addFriend("Alice");
//		System.out.println(response);
//		response = chatController.addFriend("Bob");
//		System.out.println(response);
//		response = chatController.addFriend("3aceada5-97c3-469d-8995-e512f2e1ee77");
//		System.out.println(response);
//		response = chatController.getFriend();
//		System.out.println(response);
//		response = chatController.confirmFriend("ce9443fb-f487-4f04-82bd-75282b59cac5");
//		System.out.println(response);
//		response = chatController.confirmFriend("3287832e-937c-46c5-849b-5f4bd2bdfde1");
//		System.out.println(response);
//		response = chatController.confirmFriend("3aceada5-97c3-469d-8995-e512f2e1ee77");
//		System.out.println(response);
//		response = chatController.getFriend();
//		System.out.println(response);
//		response = chatController.creatChatroom(new String[]{"Alice", "Bob"});
//		System.out.println(response);
//		chatroomID = response.msg;
//		response = chatController.sendMessage(chatroomID, "Hello");
//		System.out.println(response);
//		response = chatController.sendMessage(chatroomID, "I am Vincent");
		
		while(true) {
			//System.out.println(chatController.updateHistoryChatroom);
	        System.out.print("> ");
	        switch(scanner.nextLine()){
	        	case "register":
	        		System.out.print("Username: ");
	        		String username = scanner.nextLine();
	        		System.out.print("email: ");
	        		String email = scanner.nextLine();
	        		System.out.print("password: ");
	        		String password = scanner.nextLine();
	        		response = chatController.register(username, email, password);
	        		System.out.println(response);
	        		break;
	        	case "login":
	        		System.out.print("username: ");
	        		username = scanner.nextLine();
	        		System.out.print("password: ");
	        		password = scanner.nextLine();
	        		response = chatController.login(username, password);
	        		System.out.println(response);
	        		break;
	        	case "logout":
	        		response = chatController.logout();
	        		System.out.println(response);
	        		break;
	        	case "addFriend":
	        		System.out.print("Friend username: ");
	        		username = scanner.nextLine();
	        		response = chatController.addFriend(username);
	        		System.out.println(response);
	        		break;
	        	case "friendList":
	        		response = chatController.getFriend();
	        		System.out.println(response);
	        		break;

	        	case "confirmFriend":
	        		System.out.print("Friend username: ");
	        		username = scanner.nextLine();
	        		response = chatController.confirmFriend(username);
	        		System.out.println(response);
	        		break;

	        	case "createChatroom":
	        		System.out.println("With who? ");
	        		String[] friends = scanner.nextLine().split(" ");
	        		response = chatController.creatChatroom(friends);
	        		System.out.println(response);
	        		break;
	        	case "enterChatroom":
	        		System.out.println("Which chatroom? ");
	        		response = chatController.getChatroomList();
	        		String[] chatroomIDs = ((ChatroomList)response.info).chatroomIDs;
	        		for(int i = 0; i < chatroomIDs.length; i++) {
	        			System.out.println(i + " : " + chatroomIDs[i]);
	        		}
	        		chatroomID = chatroomIDs[scanner.nextInt()];
	        		boolean exit = false;
	        		while(!exit) {

	        			String msg = scanner.nextLine();
	        			if(msg.equals("exit")) exit = true;
	        			else {
	        				response = chatController.sendMessage(chatroomID, msg);
	        				System.out.println(response);
	        			}
	        		}
	        		break;
	        	case "exit":
	        		chatController.logout();
	        		System.exit(0);
	        }
		}
		//16773a8b-b9ab-4067-95e6-167a2e600116
//		System.out.println(response);
//		response = chatController.getHistory(chatroomID);
//		System.out.println(response);
		
	}
}

class TestUpdateHistoryThread extends Thread{
	private ChatController chatController;
	public TestUpdateHistoryThread(ChatController chatController) {
		this.chatController = chatController;
	}
	public void run() {
		while(true) {
			if(chatController.updateHistoryChatroom != null) {
				System.out.println(chatController.updateHistoryChatroom);
				Response response = chatController.getHistory(chatController.updateHistoryChatroom);
				MessageHistory messageHistory = (MessageHistory)response.info;
				for(Message message: messageHistory.messages) {
					System.out.println(message.senderID + ":" + message.msg);
				}
				chatController.updateHistoryChatroom = null;
			}
		}
	}
}

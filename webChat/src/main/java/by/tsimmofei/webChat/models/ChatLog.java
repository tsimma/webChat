package by.tsimmofei.webChat.models;

import java.util.ArrayList;
import java.util.List;

public class ChatLog {
	private static List<Message> messages = new ArrayList<>();
	
	public static List<Message> getMessages() {
		return messages;
	}

	public static void setMessages(List<Message> messages) {
		ChatLog.messages = messages;
	}
	
	
}

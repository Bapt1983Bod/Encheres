package fr.eni.ecole.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> messages;

	public BusinessException() {
		this.messages = new ArrayList<String>();
	}

	public void add(String message) {
		this.messages.add(message);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}

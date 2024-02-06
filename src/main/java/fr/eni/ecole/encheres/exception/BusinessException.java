package fr.eni.ecole.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	private List<String> messages;

	public BusinessException(String message) {
		this.messages = new ArrayList<String>();
		this.messages.add(message);
	}

	public void add(String message) {

		this.messages.add(message);

	}

	public List<String> getMessages() {
		return messages;
	}

}

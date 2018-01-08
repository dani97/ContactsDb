package com.ztech.contacts.bean;

public abstract class AbstractContactBean {

	protected String type;
	protected int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	protected String firstName;
	protected String lastName;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void sendMessage() {
		
	}
	
}

package com.ztech.contacts.bean;

public class WorkBean extends AbstractContactBean{
	private int extension;
	private long number;

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public int getExtension() {
		return extension;
	}

	public void setExtension(int extension) {
		this.extension = extension;
	}
	
}

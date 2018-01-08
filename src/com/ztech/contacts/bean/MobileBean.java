package com.ztech.contacts.bean;

public class MobileBean extends AbstractContactBean{
	private int countrycode;
	private long number;

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public int getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(int countrycode) {
		this.countrycode = countrycode;
	}
}

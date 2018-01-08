package com.ztech.contacts.Validataion;

public class ContactsVaildation {
	public static boolean validateEmail(String email) {
		return email.matches("[a-zA-Z][a-zA-Z0-9.]*@[a-zA-Z][.][a-zA-Z]{2,3}");
	}
	
	public static boolean validateNumber(long number) {
		String numberStr = Long.toString(number);
		return numberStr.matches("[7-9][0-9]{9}");
	}
	

}

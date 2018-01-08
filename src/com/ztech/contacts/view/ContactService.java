package com.ztech.contacts.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ztech.contacts.Validataion.ContactsVaildation;
import com.ztech.contacts.bean.AbstractContactBean;
import com.ztech.contacts.bean.EmailBean;
import com.ztech.contacts.bean.HomeBean;
import com.ztech.contacts.bean.MobileBean;
import com.ztech.contacts.bean.WorkBean;
import com.ztech.contacts.dao.ContactsDao;

public class ContactService {
	static Scanner scanner = new Scanner(System.in);
	static Logger logger = Logger.getLogger(ContactService.class.getName());
	
	public static void  fetchContents() {
		ContactsDao contactDao = new ContactsDao();
		logger.log(Level.INFO,"enter first name");
		try {
			ArrayList<AbstractContactBean> contactList = contactDao.fetchContacts(scanner.nextLine());
			Iterator<AbstractContactBean> it = contactList.iterator();
			while(it.hasNext()) {
				AbstractContactBean bean = it.next();
				if(bean.getType().equals("email")) {
					logger.log(Level.INFO,"First Name : " + bean.getFirstName());
					logger.log(Level.INFO,"Last Name : "+ bean.getLastName());
					EmailBean ebean = (EmailBean) bean;
					logger.log(Level.INFO,"Email ID : "+ ebean.getEmailId());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				contactDao.getDbUtil().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void insertNewContact() throws SQLException {
		ContactsDao contactDao = new ContactsDao();
		logger.log(Level.INFO,"Enter contact details\n First Name");
		String firstName = scanner.nextLine();
		String lastName = scanner.nextLine();
		String type =scanner.nextLine();
		if(type.equals("mobile")) {
			MobileBean mBean = new MobileBean();
			mBean.setFirstName(firstName);
			mBean.setLastName(lastName);
			mBean.setType(type);
			mBean.setCountrycode(scanner.nextInt());
			Long number = scanner.nextLong();
			if(ContactsVaildation.validateNumber(number)) {
				mBean.setNumber(scanner.nextLong());
			}
			contactDao.insertContact((AbstractContactBean)mBean);
		} else if (type.equals("home")) {
			HomeBean hBean = new HomeBean();
			hBean.setFirstName(firstName);
			hBean.setLastName(lastName);
			hBean.setType(type);
			logger.log(Level.INFO,"enter area code");
			hBean.setAreaCode(scanner.nextInt());
			logger.log(Level.INFO,"enter Country Code");
			hBean.setCountryCode(scanner.nextInt());
			Long number = scanner.nextLong();
			if(ContactsVaildation.validateNumber(number)) {
				hBean.setNumber(scanner.nextLong());
			}
			contactDao.insertContact((AbstractContactBean)hBean);
		} else if (type.equals("work")) {
			WorkBean wBean = new WorkBean();
			wBean.setFirstName(firstName);
			wBean.setLastName(lastName);
			wBean.setType(type);
			logger.log(Level.INFO,"enter Extension");
			wBean.setExtension(scanner.nextInt());
			Long number = scanner.nextLong();
			if(ContactsVaildation.validateNumber(number)) {
				wBean.setNumber(number);
				contactDao.insertContact((AbstractContactBean)wBean);
			}
			else {
				logger.log(Level.INFO,"wrong email");
			}
		} else if(type.equals("email")){
			EmailBean eBean = new EmailBean();
			eBean.setFirstName(firstName);
			eBean.setLastName(lastName);
			eBean.setType(type);
			logger.log(Level.INFO,"enter Email Id");
			String email = scanner.nextLine();
			if(ContactsVaildation.validateEmail(email)) {
				eBean.setEmailId(email);
				contactDao.insertContact((AbstractContactBean)eBean);
			}
			else {
				logger.log(Level.INFO,"wrong email");
			}
		}else {
			logger.log(Level.WARNING,"wrong type");;
		}
	}
	public static void main(String [] args) {
		try {
			//fetchContents();
			insertNewContact();
		}
		catch(SQLException e) {
			e.printStackTrace();
			logger.log(Level.WARNING,"errr");
		}
		
	} 

}

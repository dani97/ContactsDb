package com.ztech.contacts.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ztech.contacts.bean.AbstractContactBean;
import com.ztech.contacts.bean.EmailBean;
import com.ztech.contacts.bean.HomeBean;
import com.ztech.contacts.bean.MobileBean;
import com.ztech.contacts.bean.WorkBean;
import com.ztech.contacts.util.DBUtil;


public class ContactsDao {
	private DBUtil dbUtil;
	public DBUtil getDbUtil() {
		return dbUtil;
	}



	public void setDbUtil(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
	public static Logger logger = Logger.getLogger(ContactsDao.class.getName());
	
	public ContactsDao(){
		this.dbUtil = new DBUtil();
	}



	public void insertContact(AbstractContactBean bean) throws SQLException{
		String query1 = "insert into contacts (first_name,last_name)values(?,?)";
		PreparedStatement prestmt = dbUtil.getConnection().prepareStatement(query1);
		prestmt.setString(1, bean.getFirstName());
		prestmt.setString(2, bean.getLastName());
		prestmt.execute();
		Statement stmt = dbUtil.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT `AUTO_INCREMENT`\r\n" + 
				"FROM  INFORMATION_SCHEMA.TABLES\r\n" + 
				"WHERE TABLE_SCHEMA = 'contactsdb'\r\n" + 
				"AND   TABLE_NAME   = 'contacts';");
		while(rs.next()) {
			bean.setId(rs.getInt(1));
		}
		logger.log(Level.INFO,"processing for user "+Integer.toString(bean.getId()));
		String query2 = "insert into phone_number"+
		"(contact_type,extension,area_code,country_code,contact_number,email_id,contact_id)"+
				"values(?,?,?,?,?,?,?)";
		prestmt = dbUtil.getConnection().prepareStatement(query2);
		prestmt.setInt(7, bean.getId()-1);
		prestmt.setString(1, bean.getType());
		if(bean.getType().equals("mobile")) {
			MobileBean bean1 = (MobileBean)bean;
			prestmt.setObject(2, null);
			prestmt.setObject(3, null);
			prestmt.setObject(6, null);
			prestmt.setInt(4,bean1.getCountrycode());
			prestmt.setLong(5, bean1.getNumber());
		}
		else if(bean.getType().equals("home")) {
			HomeBean bean1 = (HomeBean)bean;
			prestmt.setObject(2, null);
			prestmt.setObject(6, null);
			prestmt.setInt(3,bean1.getAreaCode());
			prestmt.setInt(4, bean1.getCountryCode());
			prestmt.setLong(5, bean1.getNumber());
		}
		else if(bean.getType().equals("work")) {
			WorkBean bean1 = (WorkBean)bean;
			prestmt.setObject(3, null);
			prestmt.setObject(4, null);
			prestmt.setObject(6, null);
			prestmt.setInt(2, bean1.getExtension());
			prestmt.setLong(5, bean1.getNumber());
		}
		else {
			EmailBean bean1 = (EmailBean)bean;
			prestmt.setObject(2, null);
			prestmt.setObject(3, null);
			prestmt.setObject(4, null);
			prestmt.setObject(5, null);
			prestmt.setString(6, bean1.getEmailId());
		}
		prestmt.execute();
	}
	
	public ArrayList<AbstractContactBean> fetchContacts(String firstname) throws SQLException {
		String query = "select * from contacts join phone_number on contacts.contact_id = phone_number.contact_id "+
	"where contacts.first_name = ?";
		logger.log(Level.INFO,query);
		PreparedStatement prestmt = dbUtil.getConnection().prepareStatement(query);
		prestmt.setString(1, firstname);
		ResultSet rs = prestmt.executeQuery();
		ArrayList<AbstractContactBean> contactList = new ArrayList<AbstractContactBean>();
		while(rs.next()) {
			if(rs.getString("phone_number.contact_type").equals("mobile")) {
				MobileBean bean = new MobileBean();
				bean.setType(rs.getString("phone_number.contact_type"));
				bean.setId(rs.getInt("contacts.contact_id"));
				bean.setFirstName(rs.getString("contacts.first_name"));
				bean.setLastName(rs.getString("contacts.last_name"));
				bean.setNumber(rs.getLong("phone_number.contact_number"));
				bean.setCountrycode(rs.getInt("phone_number.country_code"));
				contactList.add(bean);
			}else if (rs.getString("phone_number.contact_type").equals("home")) {
				HomeBean bean = new HomeBean();
				bean.setType(rs.getString("phone_number.contact_type"));
				bean.setId(rs.getInt("contacts.contact_id"));
				bean.setFirstName(rs.getString("contacts.first_name"));
				bean.setLastName(rs.getString("contacts.last_name"));
				bean.setAreaCode(rs.getInt("phone_number.area_code"));
				bean.setCountryCode(rs.getInt("phone_number.country_code"));
				bean.setNumber(rs.getLong("phone_number.contact_number"));
				contactList.add(bean);
			}else if (rs.getString("phone_number.contact_type").equals("work")) {
				WorkBean bean = new WorkBean();
				bean.setType(rs.getString("phone_number.contact_type"));
				bean.setId(rs.getInt("contacts.contact_id"));
				bean.setFirstName(rs.getString("contacts.first_name"));
				bean.setLastName(rs.getString("contacts.last_name"));
				bean.setExtension(rs.getInt("phone_number.extension"));
				bean.setNumber(rs.getLong("phone_number.contact_number"));
				contactList.add(bean);
			}else  {
				EmailBean bean = new EmailBean();
				bean.setType(rs.getString("phone_number.contact_type"));
				bean.setId(rs.getInt("contacts.contact_id"));
				bean.setFirstName(rs.getString("contacts.first_name"));
				bean.setLastName(rs.getString("contacts.last_name"));
				bean.setEmailId(rs.getString("phone_number.email_id"));
				contactList.add(bean);
			}	
		}
		return contactList;
	}
	public static void main(String [] args) {
		ContactsDao cd = new ContactsDao();
		try {
			cd.fetchContacts("c");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dto.Address;
import dto.ContactDetails;
import dto.EmployeeDetails;
import dto.UserDetails;

public class HibernateTest {

	public static void main(String[] args){
		UserDetails user = new UserDetails();
		user.setUserName("First User");
		user.setJoinedDate(new Date());
		user.setDescription("user description");
		
		Address addr = new Address();
		addr.setStreet("Street name");
		addr.setCity("City name");
		addr.setState("State name");
		addr.setPincode("pincode number");
		user.setHomeAddress(addr);
		user.setOfficeAddress(addr);
		
		ContactDetails firstContact = new ContactDetails();
		firstContact.setName("contact name1");
		firstContact.setEmail("contact email1");
		firstContact.setPhone("contact phone1");
		ContactDetails secondContact = new ContactDetails();
		secondContact.setName("contact name2");
		secondContact.setEmail("contact email2");
		secondContact.setPhone("contact phone2");
		Set<ContactDetails> listOfContactDetails = new HashSet<ContactDetails>();
		listOfContactDetails.add(firstContact);
		listOfContactDetails.add(secondContact);
		user.setListOfContactDetails(listOfContactDetails);
		
		EmployeeDetails firstEmployee = new EmployeeDetails();
		firstEmployee.setName("Employee name1");
		firstEmployee.setEmail("Employee email1");
		firstEmployee.setPhone("Employee phone1");
		EmployeeDetails secondEmployee = new EmployeeDetails();
		secondEmployee.setName("Employee name2");
		secondEmployee.setEmail("Employee email2");
		secondEmployee.setPhone("Employee phone2");
		Collection<EmployeeDetails> listOfEmployeeDetails = new ArrayList<EmployeeDetails>();
		listOfEmployeeDetails.add(firstEmployee);
		listOfEmployeeDetails.add(secondEmployee);
		user.setListOfEmployeeDetails(listOfEmployeeDetails);
		
		/*
		 * to create a table and to push data into it, we need to create a session factory that will create a session
		 * that will begin new transaction and we will be able to commit the new object in to it
		 */
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
		
		/*
		 * to retrieve data from DB, we need to create a session that will begin new transaction. we call the "get" method
		 * and we pass the primary key of the record to it in order to fetch the row
		 */
		session = sessionFactory.openSession();
		session.beginTransaction();
		UserDetails retrievedUser = (UserDetails)session.get(UserDetails.class, 1);
		System.out.println("retrieved user name is: " + retrievedUser.getUserName());
	}
}

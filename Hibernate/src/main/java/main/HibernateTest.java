package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dto.embeddedobjects.sametable.Address;
import dto.embeddedobjects.differenttable.withoutid.ContactDetails;
import dto.embeddedobjects.differenttable.withid.EmployeeDetails;
import dto.entity.onetomany.Pet;
import dto.entity.manytomany.RentedHouse;
import dto.entity.inheritance.onetable.Desktop;
import dto.entity.inheritance.onetable.ElectronicDevice;
import dto.entity.inheritance.onetable.SmartPhone;
import dto.entity.inheritance.separatetables.Book;
import dto.entity.inheritance.separatetables.Magazine;
import dto.entity.inheritance.separatetables.ReadMaterial;
import dto.entity.mainentity.UserDetails;
import dto.entity.manytoone.UserProfile;
import dto.entity.onetoone.Vehicle;

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
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleName("Car");
		user.setVehicle(vehicle);
		
		Pet firstPet = new Pet();
		firstPet.setPetType("Dog");
		Pet secondPet = new Pet();
		secondPet.setPetType("Cat");
		Collection<Pet> pets = new ArrayList<Pet>();
		pets.add(firstPet);
		pets.add(secondPet);
		user.setPet(pets);
		
		UserProfile profile = new UserProfile();
		profile.setProfileName("manager profile");
		user.setUserProfile(profile);
		
		RentedHouse firstHouse = new RentedHouse();
		firstHouse.setHouseName("first house");
		RentedHouse secondHouse = new RentedHouse();
		secondHouse.setHouseName("second house");
		Collection<RentedHouse> rentedHouses = new ArrayList<RentedHouse>();
		rentedHouses.add(firstHouse);
		rentedHouses.add(secondHouse);
		user.setRentedHouses(rentedHouses);
		
		ElectronicDevice someDevice = new ElectronicDevice();
		someDevice.setColor("green");
		ElectronicDevice phone = new SmartPhone();
		phone.setColor("black");
		((SmartPhone)phone).setPhoneNumber(123);
		ElectronicDevice pc = new Desktop();
		pc.setColor("grey");
		((Desktop)pc).setScreensNumber(2);
		Collection<ElectronicDevice> listOfDevices = new ArrayList<ElectronicDevice>();
		listOfDevices.add(someDevice);
		listOfDevices.add(phone);
		listOfDevices.add(pc);
		user.setElectronicDevices(listOfDevices);
		
		
		ReadMaterial readMaterial = new ReadMaterial();
		readMaterial.setReadMaterialName("some read material");
		ReadMaterial book = new Book();
		book.setReadMaterialName("Gone with the wind");
		((Book)book).setAuthor("don't remember");
		ReadMaterial magazine = new Magazine();
		magazine.setReadMaterialName("Laisha");
		((Magazine)magazine).setEditor("someone");
		Collection<ReadMaterial> readMaterials = new ArrayList<ReadMaterial>();
		readMaterials.add(readMaterial);
		readMaterials.add(book);
		readMaterials.add(magazine);
		user.setReadMaterials(readMaterials);
		
		/*
		 * to create a table and to push data into it, we need to create a session factory that will create a session
		 * that will begin new transaction and we will be able to save the new object and commit the new object in to it
		 */
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		/*
		 * I am using persist method and not save in order to save also the related pets of the user. this will be done
		 * after I set a cascade property to the pet list inside UserDetails class 
		 */
		session.persist(user);
		session.save(vehicle);
//		session.save(firstPet);
//		session.save(secondPet);
		session.save(profile);
		session.save(firstHouse);
		session.save(secondHouse);
		session.save(someDevice);
		session.save(phone);
		session.save(pc);
		session.save(readMaterial);
		session.save(book);
		session.save(magazine);
		
		//this is how to update an entity
		user.setUserName("Updated user");
		session.update(user);
		//this is how to delete an entity
//		session.delete(user);
		
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

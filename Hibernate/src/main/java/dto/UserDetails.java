package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
//import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
//import javax.persistence.Table;

/*
 * Entity is object-oriented and table is relation-oriented. You can only use the entity name in the HQL (Hibernate Query Language) 
 * to query objects, and the table name in SQL
 */
@Entity(name="USER_DETAILS") //equals to table, yet the entity name has a new name
//@Table(name="USER_DETAILS") //also equals to table, yet the only the table name has a new name
public class UserDetails {

	@Id @GeneratedValue //@Id is equal to primary key, @GeneratedValue will generate the id for each record
	@Column(name="USER_ID")
	private int userId;
	
	/*
	 * if I want to have an embedded object as a primary key, I should use the annotation @EmbeddedId.
	 * this will create a primary key from the combination of the fields in the object
	 */
//	@EmbeddedId
//	private LoginName loginName;
	
	@Column(name="USER_NAME") //to give another name to the column (we can annotate the getter instead - it's the same)
	private String userName;
	
	@Temporal(TemporalType.DATE) //to save only the date and not the date and time as default - not mandatory annotation
	private Date joinedDate;
	
	@Transient //tells hibernate to ignore this field when creating the table - it will not create a corresponding column
	private String gender;
	
	@Lob //to save a huge amount of text in the column (not up till VARCHAR 255 like the default)
	private String description;
	
	private Address homeAddress;
	
	/*
	 * when we have more than 1 embedded objects of the same type, we need to give the columns of one object different names.
	 * we do that by the @AttributeOverrides annotation, that will contain multiple @AttributeOverride annotations
	 * with the new names of the columns
	 */
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="OFFICE_STREET_NAME")),
		@AttributeOverride(name="city", column=@Column(name="OFFICE_CITY_NAME")),
		@AttributeOverride(name="state", column=@Column(name="OFFICE_STATE_NAME")),
		@AttributeOverride(name="pincode", column=@Column(name="OFFICE_PIN_CODE"))
	})
	private Address officeAddress;
	
	/*
	 * when we have multiple instances of the same object that are related to this objects, we must create a separate table
	 * that will relate to this table with a foreign key. we do that by annotating the field with @ElementCollection
	 * and annotating the object itself with @Embeddable. 
	 * Hibernate has 2 fetch strategies: 1. lazy(default) 2. eager.
	 * this means that by default, any collection that is a member of this class will not be fetched when fetching the UserDetails object
	 * from DB. it will be fetched only when calling it's getter method since it saves time and resources. to tell Hibernate that
	 * we want to fetch the collection when UserDetails object is fetched, we have to use the "fetch" property with FetchType.EAGER.
	 * in order to override the default names of table and column that Hibernate gives, we should use the @JoinTable annotation.
	 * so the first one is the name of the table and the second one is the name of the generated column in the table
	 */
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="USER_CONTACTS", joinColumns=@JoinColumn(name="USER_ID"))
	private Set<ContactDetails> listOfContactDetails = new HashSet<ContactDetails>();
	
	/*
	 * same as @ElementCollection annotation, when we have multiple instances of the same object that are related to this objects,
	 * and we want to generate a primary key for it we use @CollectionId annotation. we mention the name of the field that will
	 * populate the primary key, we specify the generator and the type. besides that, we also mention the generator by using
	 * the @GenericGenerator annotation
	 */
	@ElementCollection
	@JoinTable(name="USER_EMPLOYEES", joinColumns=@JoinColumn(name="USER_ID"))
	@GenericGenerator(name="increment-gen", strategy="increment")
	@CollectionId(columns = { @Column(name="EMPLOYEE_ID") }, generator = "increment-gen", type = @Type(type="long"))
	private Collection<EmployeeDetails> listOfEmployeeDetails = new ArrayList<EmployeeDetails>();
	
	/*
	 * to connect one entity to another we need to annotate the class member entity in a @OneToOne annotation
	 * so that Hibernate will create a relation column in the object entity to the class member entity
	 */
	@OneToOne
	@JoinColumn(name="VEHICLE_ID")
	private Vehicle vehicle;
	
	/*
	 * to connect to a list of another entity we need to annotate the class member entity in a @OneToMany annotation
	 * so that Hibernate will create a new relation table from this entity to the other entity. the property cascade=CascadeType.PERSIST
	 * will save the pets of the user along with the user it self. meaning, in HibernateTest class, instead of calling session.save(user),
	 * we will call session.persist(user) and it will save also its pets (without saving them too in session).
	 * we can override the default names Hibernate gives to that relation table by joinColumns and inverseJoinColumns properties 
	 * inside @JoinTable annotation. where joinColumns will be the column referring to this table 
	 * and inverseJoinColumns will be the column referring to the member's table
	 */
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="USER_PETS", joinColumns=@JoinColumn(name="USER_ID"), inverseJoinColumns=@JoinColumn(name="PET_ID"))
	private Collection<Pet> pet = new ArrayList<Pet>();
	
	/*
	 * to connect to another entity by many to one relation we need to use annotation @ManyToOne.
	 * this will create a relation column inside this entity (and not a relation table). we can add @NotFound annotation
	 * that will handle a situation where the field is null (doesn't have a user profile), and we are telling Hibernate 
	 * to ignore the exception
	 */
	@ManyToOne
	@JoinColumn(name="PROFILE_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private UserProfile userProfile;
	
	/*
	 * to have a many to many relation we must annotate both entities with @ManyToMany annotation. if we will do only that,
	 * there 2 relation tables will be created (users_rented_houses and rented_houses_users). in order to tell Hibernate where
	 * to map this relation, we need to add "mappedBy" property in the @ManyToMany annotation of one of the entities.
	 * for example, in RentedHouse class I added the property as follows (mappedBy="rentedHouses") to mark that the mapping will be 
	 * done from field rentedHouses which is inside UserDetails class (meaning, it will be first in the relation table)
	 */
	@ManyToMany
	@JoinTable(name="USERS_RENTED_HOUSES", joinColumns=@JoinColumn(name="USER_ID"), inverseJoinColumns=@JoinColumn(name="RENTED_HOUSE_ID"))
	private Collection<RentedHouse> rentedHouses = new ArrayList<RentedHouse>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setHomeAddress(Address address) {
		this.homeAddress = address;
	}
	public Address getHomeAddress() {
		return this.homeAddress;
	}
	public Address getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}
	public Set<ContactDetails> getListOfContactDetails() {
		return listOfContactDetails;
	}
	public void setListOfContactDetails(Set<ContactDetails> listOfContactDetails) {
		this.listOfContactDetails = listOfContactDetails;
	}
	public Collection<EmployeeDetails> getListOfEmployeeDetails() {
		return listOfEmployeeDetails;
	}
	public void setListOfEmployeeDetails(Collection<EmployeeDetails> listOfEmployeeDetails) {
		this.listOfEmployeeDetails = listOfEmployeeDetails;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public Collection<Pet> getPet() {
		return pet;
	}
	public void setPet(Collection<Pet> pet) {
		this.pet = pet;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public Collection<RentedHouse> getRentedHouses() {
		return rentedHouses;
	}
	public void setRentedHouses(Collection<RentedHouse> rentedHouses) {
		this.rentedHouses = rentedHouses;
	}
}

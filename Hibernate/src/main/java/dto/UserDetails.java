package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
//import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
//import javax.persistence.Table;

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
	 * in order to override the default names of table and column that Hibernate gives, we should use the @JoinTable annotation
	 */
	@ElementCollection
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
}

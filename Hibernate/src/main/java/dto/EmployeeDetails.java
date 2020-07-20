package dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 * in order for this object to be in a separate table, we must annotate it with @Embeddable
 */
@Embeddable
public class EmployeeDetails {

	@Column(name="EMPLOYEE_NAME")
	private String name;
	
	@Column(name="EMPLOYEE_EMAIL")
	private String email;
	
	@Column(name="EMPLOYEE_PHONE")
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

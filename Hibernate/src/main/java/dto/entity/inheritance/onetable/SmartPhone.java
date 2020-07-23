package dto.entity.inheritance.onetable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SmartPhone extends ElectronicDevice {

	@Column(name="PHONE_NUMER")
	private int phoneNumber;

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	} 
}

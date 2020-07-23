package dto.entity.inheritance.onetable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Desktop extends ElectronicDevice {

	@Column(name="SCREENS_NUM")
	private int screensNumber;

	public int getScreensNumber() {
		return screensNumber;
	}

	public void setScreensNumber(int screensNumber) {
		this.screensNumber = screensNumber;
	}
}

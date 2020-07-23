package dto.entity.inheritance.onetable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*
 * Hibernate implements an inheritance by default as a single table. meaning, that all of the additional fields
 * created in the children classes are added to the same table of father class (in this case ELECTRONIC_DEVICE).
 * we can annotate the class with @Inheritance annotation to indicate that this is the strategy we take for the inheritance.
 * Hibernate creates another column by default and that is the discriminator column which populate by default 
 * the name of the class that was used (it could be the father or the children). we can annotate the class with 
 * @DiscriminatorColumn annotation to set the name of the discriminator column and its type
 */
@Entity(name="ELECTRONIC_DEVICE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DEVICE_TYPE", discriminatorType=DiscriminatorType.STRING)
public class ElectronicDevice {

	@Id @GeneratedValue
	@Column(name="DEVICE_ID")
	private int electronicId;
	
	@Column(name="DEVICE_COLOR")
	private String color;

	public int getElectronicId() {
		return electronicId;
	}

	public void setElectronicId(int electronicId) {
		this.electronicId = electronicId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}

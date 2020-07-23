package dto.entity.inheritance.separatetables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*
 * we can annotate the class with @Inheritance annotation to indicate that the strategy we take for the inheritance is table per class.
 * which means that Hibernate will create a separate table to each class (in our case, table ReadMaterial, table Book and table Magazine).
 * in the children's tables, the inherited fields from ReadMaterial class will added as columns 
 * and also the generated key from ReadMaterial class
 * 
 * there's a third way of inheritance in the Hibernate (besides one table and separate table) and it is InheritanceType.JOINED. 
 * this will create the children's tables with only the children's specific fields and the father's table with the father's fields. 
 * this is the most normalized way of inheritance. to get a full data on on of the children, we will have to query in the following manner:
 * SELECT * FROM READ_MATERIAL JOIN BOOK ON READ_MATERIAL.READ_MATERIAL_ID = BOOK.READ_MATERIAL_ID
 */
@Entity(name="READ_MATERIAL")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class ReadMaterial {

	@Id @GeneratedValue
	@Column(name="READ_MATERIAL_ID")
	private int readMaterialId;
	
	@Column(name="READ_MATERIAL_NAME")
	private String readMaterialName;

	public int getReadMaterialId() {
		return readMaterialId;
	}

	public void setReadMaterialId(int readMaterialId) {
		this.readMaterialId = readMaterialId;
	}

	public String getReadMaterialName() {
		return readMaterialName;
	}

	public void setReadMaterialName(String readMaterialName) {
		this.readMaterialName = readMaterialName;
	}
}

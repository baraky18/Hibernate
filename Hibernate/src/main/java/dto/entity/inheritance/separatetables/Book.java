package dto.entity.inheritance.separatetables;

import javax.persistence.Column;
import javax.persistence.Entity;
import dto.entity.inheritance.separatetables.ReadMaterial;

@Entity
public class Book extends ReadMaterial{
	
	@Column(name="AUTHOR_NAME")
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}

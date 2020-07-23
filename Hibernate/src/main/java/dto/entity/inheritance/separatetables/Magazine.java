package dto.entity.inheritance.separatetables;

import javax.persistence.Column;
import javax.persistence.Entity;
import dto.entity.inheritance.separatetables.ReadMaterial;

@Entity
public class Magazine extends ReadMaterial{
	
	@Column(name="EDITOR_NAME")
	private String editor;

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
}

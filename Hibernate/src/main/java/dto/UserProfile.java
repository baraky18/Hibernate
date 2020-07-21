package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="USER_PROFILE")
public class UserProfile {

	@Id @GeneratedValue
	@Column(name="PROFILE_ID")
	private int profileId;
	
	@Column(name="PROFILE_NAME")
	private String profileName;

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
}

package dto.entity.manytomany;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import dto.entity.mainentity.UserDetails;

@Entity(name="RENTED_HOUSE")
public class RentedHouse {

	@Id @GeneratedValue
	@Column(name="HOUSE_ID")
	private int houseId;
	
	@Column(name="HOUSE_NAME")
	private String houseName;
	
	@ManyToMany(mappedBy="rentedHouses")
	private Collection<UserDetails> renters = new ArrayList<UserDetails>();

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Collection<UserDetails> getRenters() {
		return renters;
	}

	public void setRenters(Collection<UserDetails> renters) {
		this.renters = renters;
	}
}

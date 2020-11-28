package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

// Represents the owner of registered local repair shops
public class Owner extends User {
	@Pattern(regexp="^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
		      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
		      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", 
		      message="Wrong phone number")
	private String phoneNumber;
	private String accountStatus;
	
	@Embedded
	// Should validate the Address fields as well when this class is being validated
	@Valid			
	private Address address;
	
	// A single owner can have many shops
	@OneToMany
	// list of shops registered by owner
	private List<Shop> shopList = new ArrayList<Shop>();
	
	public Owner(String firstName, String lastName, String email, String password, 
			String phoneNumber, Address address) {
		
		// calls User constructor first
		super(firstName, lastName, email, password);
		
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}

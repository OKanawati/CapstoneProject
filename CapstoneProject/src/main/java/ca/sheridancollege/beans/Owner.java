package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

// Represents the owner of registered local repair shops
public class Owner extends User {
	private String phoneNumber;
	private String accountStatus;
	
	@Embedded
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

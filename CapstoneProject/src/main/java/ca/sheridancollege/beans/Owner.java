package ca.sheridancollege.beans;

import javax.persistence.Entity;

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
	private String address;
	
	public Owner(String firstName, String lastName, String email, String password, 
			String phoneNumber, String address) {
		
		// calls User constructor first
		super(firstName, lastName, email, password);
		
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}

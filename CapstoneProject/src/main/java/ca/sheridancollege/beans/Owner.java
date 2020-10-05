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
	
}

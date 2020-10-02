package ca.sheridancollege.beans;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

// Represents the owner of registered local repair shops
public class Owner extends User {
	private String phoneNumber;
	private String accountStatus;
	private String address;
	
}

package ca.sheridancollege.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

// represents registered local repair shops
public class Shop {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String phoneNumber;
	private String address;
	
	// A single owner can possibly own multiple stores
	@ManyToOne(cascade=CascadeType.ALL)
	private Owner owner;
}

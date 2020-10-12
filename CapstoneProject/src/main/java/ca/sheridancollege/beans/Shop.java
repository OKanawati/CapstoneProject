package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	

	// TODO: Add list of brands that can be stored safely in the database
	
	// Many shops can be owned by a single owner
	@ManyToOne(cascade=CascadeType.ALL)
	private Owner owner;
	
	@OneToMany
	private List<Appointment> appointments = new ArrayList<Appointment>();
}

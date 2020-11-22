package ca.sheridancollege.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Shop {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String phoneNumber;
	
	@Embedded
	private Address address;
	
	// List of device brands
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Brand> brands;
	

	// TODO: Add list of brands that can be stored safely in the database
	
	// Many shops can be owned by a single owner
	@ManyToOne(cascade=CascadeType.ALL)
	private Owner owner;
	
	@OneToMany
	private List<Appointment> appointments = new ArrayList<Appointment>();
	
	private String description; // extra details that owner can use to market their shop
	private Date joinDate; // date the shop was registered
	private Boolean active; // whether shop is still operating
	
	
	
}

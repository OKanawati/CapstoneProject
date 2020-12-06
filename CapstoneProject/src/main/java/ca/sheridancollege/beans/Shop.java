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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Shop {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message="Name cannot be blank")	
	@Size(min = 2, max = 40, message="Name must be between 2 and 40 characters")
	private String name;
	@Pattern(regexp="^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
		      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
		      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", 
		      message="Wrong phone number")
	private String phoneNumber;
	
	@NotBlank(message="Must provide a start time")
	private String startTime;
	@NotBlank(message="Must provide a end time")
	private String endTime;
	private String daysClosed;
	
	@Embedded
	@Valid
	private Address address;
	
	// List of device brands
	@ManyToMany(cascade=CascadeType.ALL)
	@Size(min=1, message="Must select at least one brand")
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

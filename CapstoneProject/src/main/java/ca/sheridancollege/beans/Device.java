package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

// represents devices (i.e., cell phones) that can be serviced by local repair shops
public class Device {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Brand brand;
	
	private String releaseDate;
	private String osVersion;
	
	// contains a list of registered repair shops that can service this type of device
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Shop> shopList = new ArrayList<Shop>();
}

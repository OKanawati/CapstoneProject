package ca.sheridancollege.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
// represents an appointment for device service at a local repair shop
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String deviceBrand;
	private String services;
	private String serviceDetails;
	private String date;
	private String time;
	
	// A single customer can have many appointments
	@ManyToOne(cascade=CascadeType.ALL)
	Customer customer = new Customer();
	
	@ManyToOne(cascade=CascadeType.ALL)
	Shop shop = new Shop();
}

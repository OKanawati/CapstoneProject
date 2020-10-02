package ca.sheridancollege.beans;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

// represents an appointment for device service at a local repair shop
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String serviceDetails;
	private String date;
}

package ca.sheridancollege.beans;


import java.util.List;

import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

// represents a customer looking to get a device serviced at a local repair shop
public class Customer extends User {

	// a single customer can have multiple appointments
	@OneToMany
	private List<Appointment> appointments;
}

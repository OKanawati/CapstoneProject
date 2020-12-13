// Omar Kanawati
package ca.sheridancollege.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	private Appointment appointment;
	
	private String reviewBody;
}

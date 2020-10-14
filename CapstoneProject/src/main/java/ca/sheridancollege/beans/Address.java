package ca.sheridancollege.beans;

import javax.persistence.Embeddable;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Address {
	
	private String street;
	private String city;
	private String province;
	private String postal;
}

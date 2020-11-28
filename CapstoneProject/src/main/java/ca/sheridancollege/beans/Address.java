package ca.sheridancollege.beans;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Address {
	@NotBlank(message="Street cannot be blank")
	private String street;
	@NotBlank(message="City cannot be blank")
	private String city;
	@NotBlank(message="Province cannot be blank")
	private String province;
	@Pattern(regexp="([A-Z]\\d){3}" // Canadian Postal Codes
			+ "|^[0-9]{5}(?:-[0-9]{4})?$", // US ZIP Codes
			message="Wrong Postal Code!")
	private String postal;
}

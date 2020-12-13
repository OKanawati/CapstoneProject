// Written by Omar Kanawati
// Validation by Josh Adeyemo
package ca.sheridancollege.beans;

import java.nio.charset.Charset;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	
	@NotBlank(message="First name cannot be blank")	
	@Size(min = 2, max = 40, message="First name must be between 2 and 40 characters")
	private String custFirstName; // customer first name
	@NotBlank(message="Last name cannot be blank")	
	@Size(min = 2, max = 40, message="Last name must be between 2 and 40 characters")
	private String custLastName; // customer last name
	@Email(message = "Please enter a valid e-mail address")
	private String custEmail; // customer email
	@NotBlank(message="Must specify at least one brand")
	private String deviceBrand; // the brand of the device being serviced
	private String serviceDetails; // details inputed by the customer about what is wrong with the device
	@NotBlank(message="Must specify a Date")
	private String date; // date of the appointment
	@NotBlank(message="Must specify a Time")
	private String time; // booking time
	private String status; // current status of the appointment (i.e., Requested, Confirmed, Cancelled, Complete)
	
	// random encrypted string as key for specific appointment
	private String appointmentKey;
	
	@OneToOne
	private Review review;
	
	@ManyToOne(cascade=CascadeType.ALL)
	Shop shop = new Shop();
	
	
	// Omar Kanawati
	/**
	 * Creates a randomly generated String used as the appointmentKey appended to the Appointment URL
	 * The key length is ten characters randomly generated with special characters removed and converted
	 * to upper case.
	 * A StrinbBuffer is used to store the result.
	 * Appends the first 10 alphanumeric characters into a String.
	 * @return the randomly generated String result within the StringBuffer r.
	 */
	public static String keyGenerator() {
			
			// size of appointment key
			int n = 10;
			
			// length is bounded by 256 Character
			byte[] array = new byte[256];
			new Random().nextBytes(array);
			
			String randomKey = new String(array, Charset.forName("UTF-8"));
			
			// Create a StringBuffer to store the result
			StringBuffer r = new StringBuffer();
			
			// remove all special char and make upper case
			String AlphaNumericString = randomKey.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
			
			
			// Append first 10 alphanumeric characters from generated random String into result
			for (int i = 0; i < AlphaNumericString.length(); i++) {
				
				if (Character.isLetter(AlphaNumericString.charAt(i)) && (n > 0)
						|| Character.isDigit(AlphaNumericString.charAt(i)) && (n > 0)) {
					
					
					r.append(AlphaNumericString.charAt(i));
					n--;
				}
			}
		
			return r.toString();
		}
}

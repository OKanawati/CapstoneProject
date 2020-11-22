package ca.sheridancollege.beans;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
	
	private String custFirstName; // customer first name
	private String custLastName; // customer last name
	private String custEmail; // customer email
	private String deviceBrand; // the brand of the device being serviced
	private String serviceDetails; // details inputed by the customer about what is wrong with the device
	private String date; // date of the appointment
	private String time; // booking time
	private String status; // current status of the appointment (i.e., Requested, Confirmed, Cancelled, Complete)
	
	// random encrypted string as key for specific appointment
	private String appointmentKey;
	
	@ManyToOne(cascade=CascadeType.ALL)
	Shop shop = new Shop();
	
	
	
	public static String keyGenerator() {
			
			// size of product key
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

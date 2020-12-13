// Written by Omar Kanawati
// Validation by Josh Adeyemo
package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

// Abstract class that is to be extended by all User subclasses (i.e., Customer, Owner)
public abstract class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	// Specifies that the string cannot be spaces or empty
	@NotBlank(message="First name cannot be blank")	
	@Size(min = 2, max = 40, message="First name must be between 2 and 40 characters")
	private String firstName;
	@NotBlank(message="Last name cannot be blank")	
	@Size(min = 2, max = 40, message="Last name must be between 2 and 40 characters")
	private String lastName;
	@Email(message = "Please enter a valid e-mail address")
	private String email;
	@NotBlank(message="Password cannot be blank")
	@Size(min = 8, message="Password must be minimum 8 characters")
	private String encryptedPassword;
	private Byte enabled;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.encryptedPassword = Encryption.encryptPassword(password);
		enabled = 1;
	}
	
}

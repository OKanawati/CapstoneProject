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
	private String firstName;
	private String lastName;
	private String email;
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

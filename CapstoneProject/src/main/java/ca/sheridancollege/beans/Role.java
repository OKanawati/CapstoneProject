package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.*;

@NoArgsConstructor
@Data
@Entity

// This is to designate a database role for different Users.
// Different privileges for Customer, Owner, and Admin 
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String rolename;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="roles")
	private List<User> users = new ArrayList<User>();
	
	
}

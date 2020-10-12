package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.Owner;
import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.UserRepository;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	// displays index page
	@GetMapping("/")
	public String goHome() {
		
		return "index.html";
	}
	
	@GetMapping("/deviceRegistration")
	public String goDeviceRegistration() {
		return "deviceSupportRegistration.html";
	}
	
	// directs to shop Owner registration page
	@GetMapping("/goRegisterOwner")
	public String goRegisterOwner(Model model) {
		
		// creates a new Owner and adds it to the model
		model.addAttribute("owner", new Owner());
		
		return "registerOwner.html";
	}
	
	// registers a new shop Owner to the website
	@PostMapping("/registerOwner")
	public String registerOwner(@ModelAttribute Owner owner, Model model) {
		
		// create a new Owner using specific class constructor
		Owner registeredOwner = new Owner(owner.getFirstName(),
				owner.getLastName(),
				owner.getEmail(),
				owner.getEncryptedPassword(),
				owner.getPhoneNumber(),
				owner.getAddress());
		
		// retrieve the name for owner role and set it to new Owner
		registeredOwner.getRoles().add(roleRepo.findById(2));
		
		// saves Owner to UserRepository
		userRepo.save(registeredOwner);
		
		// returns home for testing purposes
		return "index.html";
	}
	
	// directs to customer registration page
	@GetMapping("/goRegisterCustomer")
	public String goRegisterCustomer(Model model) {
		
		// creates a new Customer and adds it to the model
		model.addAttribute("customer", new Customer());
		
		return "registerCustomer.html";
	}
	
	// registers a new customer to the website
	@PostMapping("/registerCustomer")
	public String registerCustmer(@ModelAttribute Customer customer, Model model) {
		
		// create a new Customer using specific class constructor
				Customer registeredCustomer = new Customer(customer.getFirstName(),
						customer.getLastName(),
						customer.getEmail(),
						customer.getEncryptedPassword());
				
				
				// retrieve the name for owner role and set it to new Owner
				registeredCustomer.getRoles().add(roleRepo.findById(1));
				
				// saves Owner to UserRepository
				userRepo.save(registeredCustomer);
				
				// returns home for testing purposes
				return "index.html";
	}
	
	// allows users to view account information
	@GetMapping("/viewAccount")
	public String viewAccount(Authentication authentication, Model model) {
		
		// if the user hasn't logged in, they will be redirected to the login page
		if (authentication == null) {
			return "redirect:/login";
		}
		
		// find user based on username and retrieve list of roles.
		List<Role> roles = new ArrayList<Role>();
		roles = userRepo.findByEmail(authentication.getName()).getRoles();
		
		// check list of roles
		for (Role role : roles) {
			// if one of the roles is OWNER, create a new Owner object
			if (role.getRolename().equals("ROLE_OWNER")) {
				
				// creates Owner and adds it to the model
				User owner = new Owner();
				owner = userRepo.findByEmail(authentication.getName());
				model.addAttribute("owner", owner);
				
				// send Owner user to Owner account page
				return "user/viewOwnerAccount.html";
			}
		}
		
		// If the user isn't an Owner or Admin, and the user is authenticated
		// Create Customer and add to model
		User customer = new Customer();
		customer = userRepo.findByEmail(authentication.getName());
		model.addAttribute("customer", customer);
		
		return "user/viewCustomerAccount.html";
		
	}
	
	// directs to login page
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
}

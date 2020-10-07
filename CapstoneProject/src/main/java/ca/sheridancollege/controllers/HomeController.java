package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.MessagingException;

import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.Owner;
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
	
	// controller directs user to registration page for Shop Owner
	@GetMapping("/goRegisterOwner")
	public String goRegisterOwner(Model model) {
		
		// creates a new Owner and adds it to the model
		model.addAttribute("owner", new Owner());
		
		return "registerOwner.html";
	}
	
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
	
	@GetMapping("/goRegisterCustomer")
	public String goRegisterCustomer(Model model) {
		
		// creates a new Customer and adds it to the model
		model.addAttribute("customer", new Customer());
		
		return "registerCustomer.html";
	}
	
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
	
	@GetMapping("/login")
	public String login(){
		return "loginUser.html";
	}
}

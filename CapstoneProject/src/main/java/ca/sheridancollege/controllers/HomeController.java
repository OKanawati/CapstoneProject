package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import ca.sheridancollege.beans.Brand;
import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.Owner;
import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.Shop;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.ShopRepository;
import ca.sheridancollege.repositories.UserRepository;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	ShopRepository shopRepo;
	
	// displays index page with search bar
	@GetMapping("/")
	public String goHome() {
		
		return "index.html";
	}
	
	// -------------------------REGISTRATION--------------------------------------//
	
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
		
		// directs to shop registration page
		@GetMapping("/goRegisterShop")
		public String goRegisterShop(Model model) {
			
			// create a new Shop object and attach it to model
			model.addAttribute("shop", new Shop());
			
			return "user/registerShop.html";
		}
		
		// registers new shop to currently authenticated owner
		@PostMapping("/registerShop")
		public String registerShop(@ModelAttribute Shop shop, Model model, Authentication auth) {
			
			// create Owner from authentication
			Owner owner = new Owner();
			owner = (Owner)userRepo.findByEmail(auth.getName());
			
			// create a new Shop
			Shop registeredShop = new Shop(null, shop.getName(),
					shop.getPhoneNumber(),
					shop.getAddress(),
					shop.getBrands(),
					owner, null);
			
			
			// TESTING: checking output of brands checkboxes
			System.out.println(registeredShop.getBrands());
			
			// save shop to database
			shopRepo.save(registeredShop);
			
			// add registered shop to owner
			owner.getShopList().add(registeredShop);
			userRepo.save(owner);
			
		
			return "redirect:/viewAccount";
		}
			
	// -----------------------LOGIN AND ACCOUNT INFO------------------------------//
	
	// directs to login page
		@GetMapping("/login")
		public String login() {
			return "login.html";
		}
	
	
	// allows users to view account information
	@GetMapping("/viewAccount")
	public String viewAccount(Authentication auth, Model model) {
		
		// if the user hasn't logged in, they will be redirected to the login page
		if (auth == null) {
			return "redirect:/login";
		}
		
		// find user based on username and retrieve list of roles.
		List<Role> roles = new ArrayList<Role>();
		roles = userRepo.findByEmail(auth.getName()).getRoles();
		
		// check list of roles
		for (Role role : roles) {
			// if one of the roles is OWNER, create a new Owner object
			if (role.getRolename().equals("ROLE_OWNER")) {
				
				// creates Owner and adds it to the model
				User owner = new Owner();
				owner = userRepo.findByEmail(auth.getName());
				model.addAttribute("owner", owner);
				
				// send Owner user to Owner account page
				return "user/viewOwnerAccount.html";
			}
		}
		
		// If the user isn't an Owner or Admin, and the user is authenticated
		// Create Customer and add to model
		User customer = new Customer();
		customer = userRepo.findByEmail(auth.getName());
		model.addAttribute("customer", customer);
		
		return "user/viewCustomerAccount.html";
		
	}
	
	
	// -------------------------SEARCH AND DISPLAY RESULTS------------------------//
	
	@GetMapping("/search")
	public String search(@RequestParam String search, Model model) {
	
		List<Shop> shopList = shopRepo.findAll();
		
		List<Shop> results = new ArrayList<Shop>();
		
		for (Shop shop : shopList) {
			
			for (Brand brand : shop.getBrands()) {
				
				if (brand.getBrandName().contains(search.toUpperCase())) {
					results.add(shop);
				}
			
			}
		}
		
		model.addAttribute("results", results);
		
		return "index.html";
	}
	
	// -----------------------------APPOINTMENT BOOKING---------------------------//
	
	// TODO: Appointment booking
	@GetMapping("/createAppointment")
	public String goCreateAppointment(Model model) {
		List<String> modelList = new ArrayList<String>();
		modelList.add("Moto X");
		modelList.add("SMG A10e");
		modelList.add("Pixel");
		modelList.add("Galaxy S5");
		modelList.add("IPhone X");

		ca.sheridancollege.beans.Appointment app = new ca.sheridancollege.beans.Appointment();
		app.getCustomer().setFirstName("John");
		app.getCustomer().setLastName("Doe");
		app.getCustomer().setEmail("John@email.com");
		app.setServiceDetails("This is message from the controller");
		model.addAttribute("modelList", modelList);
		model.addAttribute("appointment", app);
		
		
		
		
		return "user/createAppointment.html";
	}
	
	@GetMapping("/deviceRegistration")
	public String goDeviceRegistration() {
		return "deviceSupportRegistration.html";
	}
	
}

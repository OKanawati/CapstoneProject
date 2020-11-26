package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import ca.sheridancollege.beans.Appointment;
import ca.sheridancollege.beans.Brand;
import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.Owner;
import ca.sheridancollege.beans.Review;
import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.Shop;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repositories.AppointmentRepository;
import ca.sheridancollege.repositories.BrandRepository;
import ca.sheridancollege.repositories.ReviewRepository;
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
	
	@Autowired
	BrandRepository brandRepo;
	
	@Autowired
	AppointmentRepository appointmentRepo;
	
	@Autowired
	ReviewRepository reviewRepo;
	
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
		
		// directs to shop registration page
		@GetMapping("/goRegisterShop")
		public String goRegisterShop(Model model) {
			
			// create a new Shop object and attach it to model
			model.addAttribute("shop", new Shop());
			
			// retrieves list of device brands
			List<Brand> brands = brandRepo.findAll();
			model.addAttribute("brands", brands);
			
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
					owner, null, null, null, null);
			
			
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
		
		
		return "index.html";
		
	}
	
	
	// -------------------------SEARCH AND DISPLAY RESULTS------------------------//
	
	@GetMapping("/search")
	public String search(@RequestParam String search, Model model) {
	
		// retrieve all registered shops
		List<Shop> shopList = shopRepo.findAll();
		
		// list for shops that match search string
		List<Shop> results = new ArrayList<Shop>();
		
		// list of shop names
		List<String> shopNames = new ArrayList<String>();
		
		// list of shop IDs
		List<Integer> shopIDs = new ArrayList<Integer>();
		
		// builds a string that contains the geocoder
		StringBuilder geocoder = new StringBuilder
				("https://api.mapbox.com/geocoding/v5/mapbox.places-permanent/");
		
		// iterates through shop list
		for (Shop shop : shopList) {
			
			// iterates through each brand supported by shop
			for (Brand brand : shop.getBrands()) {
				
				// checks if brand name matches the search string
				if (brand.getBrandName().contains(search.toUpperCase())) {
					
					// adds shop if matches
					results.add(shop);
					
					// adds name to separate list
					shopNames.add(shop.getName());
					
					// adds ids to a separate lsit
					shopIDs.add(shop.getId());
				}
			
			}
		}
		
		// iterates through results and appends address parameter to geocoder
		int i = 0;
		for (Shop shop : results)  {
			
			String address = shop.getAddress().getStreet().replaceAll(" ", "%20");
			address = address.replaceAll("#", "");
			geocoder.append(address + "%20" 
					+ shop.getAddress().getCity()
					+ "%20"
					+ shop.getAddress().getProvince());
			
			i++;
			
			if (i < results.size()) {
				geocoder.append(";");
			}
		}
		
		geocoder.append(".json?country=CA&access_token=" 
		+ "pk.eyJ1Ijoib2thbmF3YXRpIiwiYSI6ImNrZzhsOXlueDBpZmYyeW8yZnFoaHplOGMifQ.oVTSOlNLzBcJN7EfekHy9g&limit=1");
		
		
		// prints geocoder string for debugging
		System.out.println(geocoder);
		
		// attaches results to model
		model.addAttribute("results", results);
		
		// attaches list of shop names to model
		model.addAttribute("shopNames", shopNames);
		
		// attaches a list of shop ids to model
		model.addAttribute("shopIDs", shopIDs);
		
		// attaches geocoder string to model
		model.addAttribute("geocoder", geocoder);
		
		return "index.html";
	}
	
	// -----------------------------APPOINTMENT BOOKING---------------------------//
	
	// TODO: Appointment booking
	@GetMapping("/createAppointment")
	public String goCreateAppointment(Model model, @RequestParam int shopID) {
		
		List<String> modelList = new ArrayList<String>();
		
		Shop shop = shopRepo.findById(shopID);
		
		// populates the list with brands serviced by store
		for (Brand brand : shop.getBrands()) {
			modelList.add(brand.getBrandName());
		}
				
		// adds a shop to the model
		model.addAttribute("shop", shop);

		Appointment app = new Appointment();
		
		app.setServiceDetails("Write your problems here");
		model.addAttribute("modelList", modelList);
		model.addAttribute("appointment", app);
		
		return "createAppointment.html";
	}
	
	@PostMapping("/saveAppointment")
	public String saveAppointment(@ModelAttribute Appointment appointment, Authentication auth,
			@RequestParam int shopID) {
		

		Shop shop = shopRepo.findById(shopID);
		
		Review review = new Review();
		
		appointment.setAppointmentKey(Appointment.keyGenerator());
		appointment.setStatus("REQUESTED");
		appointment.setReview(review);
		appointment.setShop(shop);
		
		shop.getAppointments().add(appointment);
		
		appointmentRepo.save(appointment);
		shopRepo.save(shop);
		
		//TODO: Create confirmation page to redirect to
		return "bookingConfirm.html";
	}
	
	//TODO: Implement view and editing of appointments using appointment link
	@GetMapping("/viewAppointment/{id}")
	public String viewAppointment(@PathVariable String id, Model model) {
		
		// find appointment using path variable
		Appointment appointment = appointmentRepo.findByAppointmentKey(id);
	
		// find shop that appointment belongs to
		int shopId = appointment.getShop().getId();
		Shop shop = shopRepo.findById(shopId);
		
		// find review attached to appointment even if null
		Review review = appointment.getReview();
		
		// attach appointment, shop, and review to model
		model.addAttribute("appointment", appointment);
		model.addAttribute("shop", shop);
		model.addAttribute("review", review);
		
		return "viewAppointments.html";
	}
	
}

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import ca.sheridancollege.beans.Appointment;
import ca.sheridancollege.beans.Brand;
import ca.sheridancollege.beans.Owner;
import ca.sheridancollege.beans.Review;
import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.Shop;
import ca.sheridancollege.email.EmailServiceImpl;
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
	
	@Autowired
	EmailServiceImpl esi;

	// displays index page with search bar
	// Omar Kanawati
	@GetMapping("/")
	public String goHome() {
		
		return "index.html";
	}
	
	// Josh Adeyemo
	private <T> boolean getValidationMessages(T object, Model model) {
		Validator validator = Validation
				.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> validationErrors = 
				validator.validate(object);
		
		if(!validationErrors.isEmpty()){
			List<String> errors = new ArrayList<String>();
			for(ConstraintViolation<T> error : validationErrors){
				//errors.add(error.getPropertyPath() + "::" + error.getMessage());
				errors.add(error.getMessage());
			}
			model.addAttribute("errorMessage", errors);
			return true;
		}

		return false;
	}
	// -------------------------REGISTRATION--------------------------------------//
	
	// directs to shop Owner registration page
	// Omar Kanawati
		@GetMapping("/goRegisterOwner")
		public String goRegisterOwner(Model model) {
		
			// creates a new Owner and adds it to the model
			model.addAttribute("owner", new Owner());
			
			return "registerOwner.html";
		}
		
		
		// Omar Kanawati
		/**
		 * Registers a new shop Owner to the website.
		 * Accessed from the "Register Shop Owner" link on the site's footer.
		 * It creates an new Owner object using information inputed in the Owner registration form.
		 * Once created, it assigns it the role of ROLE_OWNER.
		 * The owner is then saved to the User Repository.
		 * 
		 * @param owner	contains the model attributes of Owner collected from registerOwner.html.
		 * @param model 
		 * @return the index page.
		 */
		@PostMapping("/registerOwner")
		public String registerOwner(@ModelAttribute Owner owner, Model model) {
			
			// Josh Adeyemo
			if (getValidationMessages(owner, model)) {
				model.addAttribute("owner", owner);
				return "registerOwner.html";
			}
			
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
		
		// Omar Kanawati
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
		
		// Omar Kanawati
		/**
		 * Registers new shop to currently authenticated owner.
		 * The owner of the shop is retrieved from the User Repository by using the email found
		 * inside the Authentication object.
		 * 
		 * A new shop is created using the information inputed by the user in the form found on registerShop.html.
		 * List of appointments is null because it doesn't have any appointments booked.
		 * The fields "description", "joinDate", and "active" are null
		 * because there is currently no front-end implementation for these fields.
		 * 
		 * The new shop is saved to the Shop Repository and the Owner's Shop List is updated.
		 * @param shop contains the model attributes of a Shop collected from registerShop.html
		 * @param model
		 * @param auth contains credentials of user after successfully being authenticated
		 * @return
		 */
		@PostMapping("/registerShop")
		public String registerShop(@ModelAttribute Shop shop, Model model, Authentication auth) {
			
			// Josh Adeyemo
			if (getValidationMessages(shop, model)) {
				model.addAttribute("shop", shop);
		
				List<Brand> brands = brandRepo.findAll();
				// IMPORTANT: (Josh)
				// The 3 lines below prevent a stack overflow error
				// when performing validation
				// as it recursively calls brand.toSring() -> shop.toString()
				// (occurs only if there is a validation error with any of the shop fields
				// but not the brand field)
				for(Brand b : brands) {
					b.setShops(null);
				}
				model.addAttribute("brands", brands);
				
				return "user/registerShop.html";
			}
			
			// create Owner from authentication
			Owner owner = new Owner();
			owner = (Owner)userRepo.findByEmail(auth.getName());
			
			// create a new Shop
			// List of appointments is null because it doesn't have any appointments booked.
			// "description", "joinDate", and "active" are null.
			// There is currently no front-end implementation for these fields.
			Shop registeredShop = new Shop(null, shop.getName(),
					shop.getPhoneNumber(),
					shop.getStartTime(),
					shop.getEndTime(),
					shop.getDaysClosed(),
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
		// Omar Kanawati
		@GetMapping("/login")
		public String login() {
			return "login.html";
		}
	
	
	// Omar Kanawati
	/**
	 * Allows users to view account information such as shops owned and appointments booked.
	 * If the user isn't currently logged in, they are redirected to login to be authenticated.
	 * Once authenticated, a list of of roles is created after retrieving them from the user.
	 * The role list is checked to see if one of the roles contains ROLE_OWNER.
	 * If it passes the check, an Owner object is created by fetching the user from the User Repository
	 * using the email address inside the Authentication object.
	 * 
	 * A list of appointments is created by iterating through every shop that belongs to the Owner
	 * and retrieving the appointments booked for each Shop.
	 * 
	 * Both the owner and the appointments list is added to the model.
	 * 
	 * @param auth contains credentials of user after successfully being authenticated
	 * @param model holds the attributes of the created Owner to be displayed in viewOwnerAccount.html
	 * @return owner account page viewOwnerAccount.html
	 */
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
				Owner owner = new Owner();
				owner = (Owner) userRepo.findByEmail(auth.getName());
				model.addAttribute("owner", owner);
				
				
				List<Appointment> appointments = new ArrayList<Appointment>();
				
				for (Shop shop : owner.getShopList()) {
					for (Appointment appointment : shop.getAppointments()) {
						appointments.add(appointment);
					}
				}
				model.addAttribute("appointments", appointments);
				
				// send Owner user to Owner account page
				return "user/viewOwnerAccount.html";
			}
		}
		
		
		return "index.html";
		
	}
	
	// Omar Kanawati
	/**
	 * Filters the list of appointments displayed in the appointments table at viewOwnerAccount.html.
	 * The Owner is retrieved from the User Repository that matches the id found in the Request Parameter.
	 * An empty list of appointments is created, and every appointment found inside each Shop belonging to the Owner
	 * that matches the status parameter is added to the list.
	 * Both the appointment list and owner are added to the model and viewOwnerAccount.html is returned.
	 * @param status Request Parameter selected from filter drop down box.
	 * @param ownerId integer used to find current Owner from User Repository
	 * @param model holds attributes of Owner and new appointment list to be displayed at viewOwnerAccount.html
	 * @return owner account page in viewOwnerAccount.html
	 */
	@GetMapping("/filterAppointments")
	public String filterAppointments(@RequestParam String status, @RequestParam int ownerId, Model model) {
		
		// Find an Owner in the User Repository using the ownerId request parameter
		Owner owner = new Owner();
		owner = (Owner) userRepo.findById(ownerId);
		
		// create an empty list of appointments
		List<Appointment> appointments = new ArrayList<Appointment>();
		
		// retrieve every appointment from every Shop belonging to the Owner and add it to the appointment list
		for (Shop shop : owner.getShopList()) {
			for (Appointment appointment : shop.getAppointments()) {
				
				// if the status selected in drop down menu is "NONE", add all appointments to the list
				if (status.contains("NONE")) {
					appointments.add(appointment);
				}
				
				// checks the selected status from the drop down menu and only displays appointments with that status
				else if (appointment.getStatus().contains(status)) {
					appointments.add(appointment);
				}
			}
		}
		
		// owner and new list of appointments is added to the model
		model.addAttribute("owner", owner);
		model.addAttribute("appointments", appointments);
				
		return "user/viewOwnerAccount.html";
	}
	
	
	// -------------------------SEARCH AND DISPLAY RESULTS------------------------//
	
	
	// Omar Kanawati
	/**
	 * Searches for every registered Shop that supports the device Brand searched by the User.
	 * A list of Brands is created by searching the Brand Repository of all Brands that contain
	 * the search String.
	 * 
	 * A Set of Shops that match the Brands in the Brands List is created, with duplicates not included
	 * if a Shop supports more than one Brand found in the list.
	 * 
	 * A geocoder parameter String is built using the address information found in each Shop,
	 * and is combined with the MapBox Geocoder API call to perform Batch Geocoding on the list of Shops.
	 * 
	 * The Shop List, Shop Names, Shop IDs, and the full API call String are added to the model where they
	 * will be used in initializing the Map and creating an intractable list of Shops at the index page.
	 * @param search a String inputed by the User containing the brand name of the device they need serviced.
	 * @param model holds attributes of search results, shop names and IDs, 
	 * 		  		and a Mapbox Geocoder API call used at the index page.
	 * @return 	the index page
	 */
	@GetMapping("/search")
	public String search(@RequestParam String search, Model model) {
		
		// find the brand that matches the search
		List<Brand> brands = brandRepo.findByBrandNameContains(search.toUpperCase());

		Set<Shop> results = new HashSet<Shop>();
		
		for (Brand brand : brands) {
			
			for (Shop shop: brand.getShops()) {
				results.add(shop);
			}
		}
		
		// list of shop names
		List<String> shopNames = new ArrayList<String>();
		
		// list of shop IDs
		List<Integer> shopIDs = new ArrayList<Integer>();
		
		// builds a string that contains the geocoder
		StringBuilder geocoder = new StringBuilder
				("https://api.mapbox.com/geocoding/v5/mapbox.places-permanent/");
		
		// iterates through shop list
		for (Shop shop : results) {

			// adds name to separate list
			shopNames.add(shop.getName());

			// adds ids to a separate lsit
			shopIDs.add(shop.getId());

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
	
	// ------------------------------DISTANCE CHECKING(Unused)-----------------------------//
	//Thomas Forber
	/**
	 * Takes the longitude and latitude of a store and the customer, and uses the "haversine" formula to calculate
	 * the shortest straight line distance between the two points using a Great Circle line.
	 * @param float array containing coordinates of the store to check
	 * @param float array containing coordinates of the customer's location
	 * @param distance parameter requested by customer to search within
	 * @return true if store is within distance, false if outside
	 */

	private boolean isClose(float[] storeLoc, float[] custLoc, int dist) {
		final double RADIUS = 6371008.8;
		double storeLong = storeLoc[0] * Math.PI / 180;
		double storeLat = storeLoc[1] * Math.PI / 180;

		double custLong = custLoc[0] * Math.PI / 180;
		double custLat = custLoc[1] * Math.PI / 180;

		double deltaLong = storeLong - custLong;
		double deltaLat = storeLat - custLat;

		double step1 = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(storeLat) * Math.cos(custLat) * Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2);
		double step2 = 2 * Math.atan2(Math.sqrt(step1), Math.sqrt(1 - step1)) * RADIUS;
		double distInKm = step2 / 1000;
		if (distInKm <= dist)
			return true;
		else
			return false;
	}
	
	// -----------------------------APPOINTMENT BOOKING---------------------------//
	
	// Omar Kanawati and Josh Adeyemo
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
	
	// Omar Kanawati
	/**
	 * Saves the Appointment made by the customer and sends a confirmation email with a link to the Appointment.
	 * Uses the Request Parameter shopID to find the current Shop the customer is booking with.
	 * A key string is randomly generated and assigned to the appointment, and its status is set to "Requested".
	 * The appointment is assigned to the Shop, and the Shop adds the appointment to its appointment list.
	 * A confirmation email with the link to the appointment is sent to the customer, and the user is
	 * redirected to a confirmation page.
	 * @param appointment created using the Appointment information inputed by the customer at createAppointment.html
	 * @param model holds the attributes for the Shop, list of brands, and appointment
	 * @param auth contains the credentials of the authorized User
	 * @param shopID Integer id of the Shop the customer is booking an appointment with
	 * @return the booking confirmation page
	 */
	@PostMapping("/saveAppointment")
	public String saveAppointment(@ModelAttribute Appointment appointment, Model model, Authentication auth,
			@RequestParam int shopID) {
		
		// Josh Adeyemo
		if (getValidationMessages(appointment, model)) {

			List<String> modelList = new ArrayList<String>();
			Shop shop = shopRepo.findById(shopID);
			for (Brand brand : shop.getBrands()) {
				modelList.add(brand.getBrandName());
			}
			model.addAttribute("shop", shop);
			model.addAttribute("modelList", modelList);
			model.addAttribute("appointment", appointment);
			
			return "createAppointment.html";
		}

		// retrieves Shop using ID after Book Appointment button is clicked
		Shop shop = shopRepo.findById(shopID);
		
		// Generates a unique appointment key for the appointment
		// Sets appointment status to "Requested"
		appointment.setAppointmentKey(Appointment.keyGenerator());
		appointment.setStatus("REQUESTED");
		appointment.setShop(shop);
		
		// adds the new appointment to the Shop
		shop.getAppointments().add(appointment);
		
		// Appointment is saved to Appointment Repository
		// Shop is saved after updated with new Appointment
		appointmentRepo.save(appointment);
		shopRepo.save(shop);
		
		// sends a confirmation email to customer with link to Appointment
		sendConfirmationEmail(shop, appointment);
		
		// directs user to confirmation page telling customer to check email
		return "bookingConfirm.html";
	}
	
	// Omar Kanawati
	@GetMapping("/updateStatus/{id}/{status}")
	public String updateStatus(@PathVariable int id, @PathVariable String status, Model model) {
		
		Appointment appointment = appointmentRepo.findById(id);
		
		appointment.setStatus(status);
		
		appointmentRepo.save(appointment);
				
		return "redirect:/viewAccount";
		
	}
	
	// Omar Kanawati
	/**
	 * Implement view and editing of appointments using appointment link.
	 * Finds an Appointment using the appointment key path variable in the URL path.
	 * Finds the shop the appointment belongs to by retrieving the shop ID inside the appointment.
	 * Retrieves a Review assigned to the appointment even if the value is null
	 * Attaches the Appointment, Shop, and Review to the model.
	 * @param id the generated key String for the appointment.
	 * @param model holds the attributes for the Appointment, Shop, and Review which are displayed at ViewAppointments
	 * @return viewAppointments page where customer can review the appointment they made
	 */
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
	
	// Omar Kanawati
	/**
	 * Creates a new review using the review body text written by the customer, and adds it to the appointment.
	 * The requested appointment is retrieved using the appointment key found in the URL path
	 * A new Review is created and its reviewBody is set as the customer's written review.
	 * The Review is set to the Appointment, and is saved in the Review Repository.
	 * The Appointment is set with the saved Review, 
	 * and the updated Appointment is saved to the Appointment Repository.
	 * The customer is redirected to the appointment they requested with the new review added.
	 * @param reviewBody review text written by the customer
	 * @param appointmentKey randomly generated key string for the appointment.
	 * @param model
	 * @return URL for viewAppointment page with the appointmentKey as a Path Variable
	 */
	@GetMapping("/reviewShop")
	public String reviewShop(@RequestParam String reviewBody, @RequestParam String appointmentKey, Model model) {
		
		// Creates a new Review and finds an Appointment using the appointment key
		Review review = new Review();
		Appointment appointment = appointmentRepo.findByAppointmentKey(appointmentKey);
		
		// Sets the appointment to the new review
		// Adds the review body that the customer wrote into the Review object
		// Saves review to Review Repository
		review.setAppointment(appointment);
		review.setReviewBody(reviewBody);
		reviewRepo.save(review);
		
		// Sets the review to the correct Appointment
		// Saves the updated Appointment to the Appointment Repository
		appointment.setReview(review);
		appointmentRepo.save(appointment);
	
		// redirects to the requested appointment page
		return "redirect:/viewAppointment/" + appointmentKey;
		
	}
	
	// -----------------------------SENDING EMAILS---------------------------//
	
	// Omar Kanawati
	/**
	 * Sends a confirmation email with a message containing a link to the Appointment booked by the Customer
	 * The URL to the viewAppointment page is appended with the appointment key found in the Appointment
	 * The email requires a title, header, messageBody, footer, subject, and to parameter.
	 * @param shop the Shop the customer booked an appointment with
	 * @param appointment the Appointment the customer created that contains the appointment key
	 */
	public void sendConfirmationEmail(Shop shop, Appointment appointment)  {
		
		// url and anchor tag for appointment link
		String appUrl = "https://capstone-oddball-localfix.herokuapp.com/viewAppointment/" + appointment.getAppointmentKey();
		String link = "<a href='" + appUrl + "'>this link</a>";
		
		// email requires title, header, messageBody, footer, subject, and to
		String message = "Hello, " + appointment.getCustFirstName() + "<br>"
				+ "Thank you for using localFix. "
				+ "Your request for service at " + shop.getName() + " has been sent. "
				+ "You can view details about your appointment, "
				+ "as well as check its status, at " + link;
		
			
		try {
			esi.sendMailWithInline("Appointment Requested", 
					"Request sent to " + shop.getName(), 
					message, 
					"localFix",
					appointment.getCustEmail(),
					"Service request at " + shop.getName());
		} catch (MessagingException e) {
			System.out.println(e);
		}
		
	}
	
}

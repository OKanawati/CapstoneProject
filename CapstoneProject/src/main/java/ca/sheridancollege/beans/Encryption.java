// Omar Kanawati
package ca.sheridancollege.beans;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// A utility class meant to encrypt plaintext passwords with BCryptPasswordEncoder
public class Encryption {
	
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}

// Omar Kanawati
package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	public User findById(int id);
	public List<User> findAll();
	public User findByEmail(String email);
}

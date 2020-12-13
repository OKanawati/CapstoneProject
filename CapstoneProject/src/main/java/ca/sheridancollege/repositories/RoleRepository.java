// Omar Kanawati
package ca.sheridancollege.repositories;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	
	Role findById(int id);
}

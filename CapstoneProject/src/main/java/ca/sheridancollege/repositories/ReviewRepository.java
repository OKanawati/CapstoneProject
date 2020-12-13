// Omar Kanawati
package ca.sheridancollege.repositories;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

	public Review findById(int id);
}

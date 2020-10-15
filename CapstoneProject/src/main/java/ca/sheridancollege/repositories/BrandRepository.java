package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Brand;

public interface BrandRepository extends CrudRepository<Brand, Integer> {
	
	Brand findById(int id);
	public List<Brand> findAll();
}

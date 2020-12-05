package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Brand;

public interface BrandRepository extends CrudRepository<Brand, Integer> {
	
	Brand findById(int id);
	Brand findByBrandName(String name);
	public List<Brand> findAll();
}

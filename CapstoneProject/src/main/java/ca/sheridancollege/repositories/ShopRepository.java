package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Shop;

public interface ShopRepository extends CrudRepository<Shop, Integer> {
	
	public Shop findById(int id);
	public Shop findByPhoneNumber(String phoneNumber);
	public List<Shop> findAll();
}

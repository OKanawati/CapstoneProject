package ca.sheridancollege.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Brand {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String brandName;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Device> devices;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="brands")
	private List<Shop> shops;
}

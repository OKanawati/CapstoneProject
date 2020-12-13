// Omar Kanawati
package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

	public Appointment findById(int id);
	public Appointment findByAppointmentKey(String appointmentKey);
	public List<Appointment> findAll();
}

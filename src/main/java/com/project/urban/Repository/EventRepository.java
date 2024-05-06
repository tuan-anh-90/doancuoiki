
package com.project.urban.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.project.urban.Entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Query("from Event e where not(e.end < :from or e.start > :to)")
	public List<Event> findBetween(@Param("from") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
			@Param("to") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end);

	List<Event> findAllByUserEmail(String email);

	@Query("from Event e where not(e.end < :from or e.start > :to) and e.user.email = :email")
	public List<Event> findBetweenAndEmail(@Param("from") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
			@Param("to") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end, @Param("email") String email);

	@Query("from Event e where not(e.end < :from or e.start > :to) and e.eventLocation = :email")
	public List<Event> findBetweenAndLocation(@Param("from") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
			@Param("to") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end, @Param("email") String eventLocation);

}
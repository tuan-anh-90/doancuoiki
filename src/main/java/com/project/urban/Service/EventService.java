package com.project.urban.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.project.urban.Controller.CalendarController.EventCreateParams;
import com.project.urban.Controller.CalendarController.EventMoveParams;
import com.project.urban.Controller.CalendarController.SetColorParams;
import com.project.urban.DTO.EventDTO;
import com.project.urban.Entity.Event;

@Service
public interface EventService {
    Iterable<Event> findEventsBetween(LocalDateTime start, LocalDateTime end);

    Iterable<Event> findEventsBetweenAndEmail(LocalDateTime start, LocalDateTime end, String email);

    Iterable<Event> findEventsBetweenAndLocation(LocalDateTime start, LocalDateTime end, String eventLocation);

    Event createEvent(EventCreateParams params);

    Event moveEvent(EventMoveParams params);

    Event setColor(SetColorParams params);

    void deleteEvent(Long id);

    List<EventDTO> getAllEvents();

    List<EventDTO> getByEmail(String email);
}

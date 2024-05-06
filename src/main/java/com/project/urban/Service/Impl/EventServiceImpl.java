package com.project.urban.Service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import com.project.urban.Repository.UserRepository;
import com.project.urban.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import com.project.urban.Controller.CalendarController.EventCreateParams;
import com.project.urban.Controller.CalendarController.EventMoveParams;
import com.project.urban.Controller.CalendarController.SetColorParams;
import com.project.urban.DTO.EventDTO;
import com.project.urban.Entity.Event;
import com.project.urban.Repository.EventRepository;
import com.project.urban.Service.EventService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Iterable<Event> findEventsBetweenAndLocation(LocalDateTime start, LocalDateTime end, String eventLocation) {
        return eventRepository.findBetweenAndLocation(start, end, eventLocation);
    }

    @Override
    public Iterable<Event> findEventsBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findBetween(start, end);
    }

    @Override
    public Iterable<Event> findEventsBetweenAndEmail(LocalDateTime start, LocalDateTime end, String email) {
        return eventRepository.findBetweenAndEmail(start, end, email);
    }

    @Override
    public Event createEvent(EventCreateParams params) {
        Event event = new Event();
        event.setStart(params.start);
        event.setEnd(params.end);
        event.setText(params.text);
        event.setEventLocation(params.eventLocation);
        event.setEventNotes(params.eventNotes);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setUser(userRepository.findByEmail(userDetails.getUsername()).get());
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event moveEvent(EventMoveParams params) {
        Optional<Event> optionalEvent = eventRepository.findById(params.id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setStart(params.start);
            event.setEnd(params.end);
            eventRepository.save(event);
            return event;
        } else {
            throw new IllegalArgumentException("Event not found");
        }
    }

    @Override
    public Event setColor(SetColorParams params) {
        Optional<Event> optionalEvent = eventRepository.findById(params.id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setColor(params.color);
            eventRepository.save(event);
            return event;
        } else {
            throw new IllegalArgumentException("Event not found");
        }
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<EventDTO> allEvents = new ArrayList<>();
        List<Event> events = eventRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        for (Event event : events) {
            EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
            eventDTO.setText(event.getText());
            eventDTO.setColer(event.getColor());
            eventDTO.setStartTime(event.getStart());
            eventDTO.setEndTime(event.getEnd());
            allEvents.add(eventDTO);
        }
        return allEvents;
    }

    @Override
    public List<EventDTO> getByEmail(String email) {
        List<EventDTO> allEvents = new ArrayList<>();
        List<Event> events = eventRepository.findAllByUserEmail(email);
        ModelMapper modelMapper = new ModelMapper();

        for (Event event : events) {
            EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
            eventDTO.setText(event.getText());
            eventDTO.setColer(event.getColor());
            eventDTO.setStartTime(event.getStart());
            eventDTO.setEndTime(event.getEnd());
            allEvents.add(eventDTO);
        }
        return allEvents;
    }
}

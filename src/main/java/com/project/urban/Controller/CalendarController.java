
package com.project.urban.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.urban.Entity.Event;
import com.project.urban.Repository.EventRepository;
import com.project.urban.Service.EventService;
import com.project.urban.DTO.EventDTO;
import com.project.urban.DTO.UserDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CalendarController {

    private final EventService eventService;

    @GetMapping("/event/all")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Iterable<Event> eventsAll(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return eventService.findEventsBetween(start, end);
    }

    @GetMapping("/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return eventService.findEventsBetweenAndEmail(start, end, userDetails.getUsername());
    }

    @PostMapping("/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Event createEvent(@RequestBody EventCreateParams params) {
        return eventService.createEvent(params);
    }

    @PostMapping("/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Event moveEvent(@RequestBody EventMoveParams params) {
        return eventService.moveEvent(params);
    }

    @PostMapping("/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Event setColor(@RequestBody SetColorParams params) {
        return eventService.setColor(params);
    }

    @PostMapping("/events/delete")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public EventDeleteResponse deleteEvent(@RequestBody EventDeleteParams params) {
        eventService.deleteEvent(params.id);
        return new EventDeleteResponse("Deleted");
    }

    @GetMapping("/events/search")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end, @RequestParam String email) {
        return eventService.findEventsBetweenAndEmail(start, end, email);
    }

    @GetMapping("/events/searchLocation")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public Iterable<Event> events1(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end,
            @RequestParam String eventLocation) {
        return eventService.findEventsBetweenAndLocation(start, end, eventLocation);
    }

    public static class EventDeleteParams {
        public Long id;
    }

    public static class EventDeleteResponse {
        public String message;

        public EventDeleteResponse(String message) {
            this.message = message;
        }
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;
        public String eventLocation;
        public String eventNotes;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }

}
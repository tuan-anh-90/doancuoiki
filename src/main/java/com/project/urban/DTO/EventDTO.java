package com.project.urban.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventDTO {

    private String text;

    private String coler;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String eventLocation;

    private String eventNotes;

}
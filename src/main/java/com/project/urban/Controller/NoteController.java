package com.project.urban.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.urban.DTO.NoteDTO;
import com.project.urban.Service.NoteService;
import com.project.urban.Entity.Note;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/")
    public ResponseEntity<?> createNote(@RequestBody Note noteDTO) {
        Note savedNoteDTO = noteService.createNote(noteDTO);

        return new ResponseEntity<>(savedNoteDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<NoteDTO>> getAllNote() {
        List<NoteDTO> notes = noteService.getAllNotes();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") Long noteId) {
        noteService.deleteNote(noteId);
        return new ResponseEntity<>("Note successfully deleted!", HttpStatus.OK);
    }
}
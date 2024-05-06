package com.project.urban.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.project.urban.Entity.Note;
import com.project.urban.DTO.NoteDTO;

@Service
public interface NoteService {

    Note createNote(Note note);

    void deleteNote(Long noteId);

    NoteDTO editNote(NoteDTO noteDTO);

    List<NoteDTO> getAllNotes();
}

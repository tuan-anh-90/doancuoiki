package com.project.urban.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.urban.DTO.NoteDTO;
import com.project.urban.Entity.Note;
import com.project.urban.Entity.User;
import com.project.urban.Repository.NoteRepository;
import com.project.urban.Repository.UserRepository;
import com.project.urban.Service.NoteService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    // @Override
    // public Note createNote(Note note) {
    // Note savedNote = noteRepository.save(note);
    // System.out.println("Saved Note: " + savedNote);
    // return savedNote;
    // }
    @Override
    public Note createNote(Note note) {
        // Thực hiện xác nhận thông tin người dùng (nếu cần)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            Optional<User> userOptional = userRepository.findByEmail(username);

            if (userOptional.isPresent()) {
                // Thiết lập thông tin người dùng cho Note
                note.setUser(userOptional.get());

                // Lưu đối tượng Note vào cơ sở dữ liệu
                Note savedNote = noteRepository.save(note);

                System.out.println("Saved Note: " + savedNote);

                return savedNote;
            } else {
                // Xử lý khi không tìm thấy người dùng trong cơ sở dữ liệu
                throw new RuntimeException("User not found in the database");
            }
        } else {
            // Xử lý khi không có thông tin người dùng hoặc không phải là UserDetails
            throw new RuntimeException("User not authenticated or UserDetails not found");
        }
    }

    @Override
    public void deleteNote(Long noteID) {
        Note note = noteRepository.findById(noteID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "note not found"));
        noteRepository.deleteById(note.getId());

    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<NoteDTO> allNote = new ArrayList<>();
        List<Note> notes = noteRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        for (Note note : notes) {
            NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
            allNote.add(noteDTO);
        }
        return allNote;
    }

    @Override
    public NoteDTO editNote(NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(noteDTO, existingNote);
        Note updatedUser = noteRepository.save(existingNote);
        NoteDTO updatedNoteDTO = modelMapper.map(updatedUser, NoteDTO.class);
        return updatedNoteDTO;
    }

}
package com.project.urban.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.urban.Entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}

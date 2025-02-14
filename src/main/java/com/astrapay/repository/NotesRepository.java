package com.astrapay.repository;

import com.astrapay.entity.Notes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NotesRepository  {
    private final List<Notes> notesList = new ArrayList<>();

    public Notes save(Notes note) {
        Optional<Notes> existingNote = findById(note.getId());
        if (existingNote.isPresent()) {
            notesList.remove(existingNote.get());
        }
        notesList.add(note);
        return note;
    }

    public List<Notes> findAll() {
        return notesList;
    }

    public Optional<Notes> findById(Long id) {
        return notesList.stream().filter(note -> note.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        notesList.removeIf(note -> note.getId().equals(id));
    }

    public boolean existsById(Long id) {
        return notesList.stream().anyMatch(note -> note.getId().equals(id));
    }
}

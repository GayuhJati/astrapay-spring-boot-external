package com.astrapay.service;

import com.astrapay.dto.APIResponse;
import com.astrapay.dto.NotesDto;
import com.astrapay.dto.NotesRequestDto;
import com.astrapay.entity.Notes;
import com.astrapay.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final List<Notes> notes = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);
    private final ModelMapper modelMapper;
    private final NotesRepository notesRepository;

    public APIResponse<List<NotesDto>> getAllNotes() {
        List<Notes> notes = notesRepository.findAll();
        if (notes == null) {
            return new APIResponse<>("Note not found");
        }
        List<NotesDto> response = notes.stream().map(note -> modelMapper.map(note, NotesDto.class)).collect(Collectors.toList());
        return new APIResponse<>(response, HttpStatus.OK);
    }

    public APIResponse<String> createNote(NotesRequestDto request) {
        Notes entity = modelMapper.map(request, Notes.class);
        entity.setId(counter.getAndIncrement());
        notesRepository.save(entity);
        return new APIResponse<>("Notes created successfully");
    }

    public APIResponse<String> updateNote(Long id, NotesRequestDto request) {
        Notes existingNote = notesRepository.findById(id).orElse(null);
        if (existingNote == null) {
            return new APIResponse<>("Notes not found");
        }
        modelMapper.map(request, existingNote);
        notesRepository.save(existingNote);
        return new APIResponse<>("Note updated successfully");
    }

    public APIResponse<String> deleteNote(Long id) {
        notesRepository.deleteById(id);
        return new APIResponse<>("Note deleted successfully");
    }

    public APIResponse<NotesDto> getNoteById(Long id) {
        Notes notes = notesRepository.findById(id).orElse(null);
        if (notes == null) {
            return new APIResponse<>("Notes not found");
        }
        return new APIResponse<>(modelMapper.map(notes, NotesDto.class), HttpStatus.OK);
    }
}

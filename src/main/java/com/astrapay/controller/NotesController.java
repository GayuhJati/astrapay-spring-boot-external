package com.astrapay.controller;

import com.astrapay.dto.APIResponse;
import com.astrapay.dto.NotesDto;
import com.astrapay.dto.NotesRequestDto;
import com.astrapay.entity.Notes;
import com.astrapay.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotesController {

    private final NotesService noteService;

    @GetMapping
    public ResponseEntity<APIResponse<List<NotesDto>>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<NotesDto>> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping
    public ResponseEntity<APIResponse<String>> createNote(@RequestBody NotesRequestDto request) {
        APIResponse<String> response = noteService.createNote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<String>> updateNote(@PathVariable Long id, @RequestBody NotesRequestDto request) {
        APIResponse<String> response = noteService.updateNote(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteNote(@PathVariable Long id) {
        APIResponse<String> response = noteService.deleteNote(id);
        return ResponseEntity.ok(response);
    }
}

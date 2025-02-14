package com.astrapay.service;

import com.astrapay.dto.APIResponse;
import com.astrapay.dto.NotesDto;
import com.astrapay.dto.NotesRequestDto;
import com.astrapay.entity.Notes;
import com.astrapay.repository.NotesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotesServiceTest {

    @InjectMocks
    private NotesService notesService;

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private ModelMapper modelMapper;

    private NotesRequestDto notesRequestDto;
    private Notes notesEntity;
    private NotesDto notesDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup DTO and Entity for testing
        notesRequestDto = new NotesRequestDto();
        notesRequestDto.setTitle("Test Title");
        notesRequestDto.setContent("Test Content");

        notesEntity = new Notes();
        notesEntity.setId(1L);
        notesEntity.setTitle("Test Title");
        notesEntity.setContent("Test Content");

        notesDto = new NotesDto();
        notesDto.setId(1L);
        notesDto.setTitle("Test Title");
        notesDto.setContent("Test Content");
    }

    @Test
    void testGetAllNotesSuccess() {
        // Given
        when(notesRepository.findAll()).thenReturn(List.of(notesEntity));
        when(modelMapper.map(notesEntity, NotesDto.class)).thenReturn(notesDto);

        // When
        APIResponse<List<NotesDto>> response = notesService.getAllNotes();

        // Then
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("Test Title", response.getData().get(0).getTitle());
    }

    @Test
    void testCreateNoteSuccess() {
        // Given
        when(modelMapper.map(notesRequestDto, Notes.class)).thenReturn(notesEntity);
        when(notesRepository.save(any(Notes.class))).thenReturn(notesEntity);

        // When
        APIResponse<String> response = notesService.createNote(notesRequestDto);

        // Then
        assertNotNull(response.getMessage());
        assertEquals(1, response.getMessage().size());
        assertEquals("Notes created successfully", response.getMessage().get(0));
    }

    @Test
    void testUpdateNoteSuccess() {
        // Given
        when(notesRepository.findById(1L)).thenReturn(Optional.of(notesEntity));
        when(modelMapper.map(notesRequestDto, Notes.class)).thenReturn(notesEntity);
        when(notesRepository.save(any(Notes.class))).thenReturn(notesEntity);

        // When
        APIResponse<String> response = notesService.updateNote(1L, notesRequestDto);

        // Then
        assertNotNull(response.getMessage());
        assertEquals(1, response.getMessage().size());
        assertEquals("Note updated successfully", response.getMessage().get(0));
    }

    @Test
    void testDeleteNoteSuccess() {
        // Given
        doNothing().when(notesRepository).deleteById(1L);

        // When
        APIResponse<String> response = notesService.deleteNote(1L);

        // Then
        assertNotNull(response.getMessage());
        assertEquals(1, response.getMessage().size());
        assertEquals("Note deleted successfully", response.getMessage().get(0));
    }

    @Test
    void testGetNoteByIdSuccess() {
        // Given
        when(notesRepository.findById(1L)).thenReturn(Optional.of(notesEntity));
        when(modelMapper.map(notesEntity, NotesDto.class)).thenReturn(notesDto);

        // When
        APIResponse<NotesDto> response = notesService.getNoteById(1L);

        // Then
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getData());
        assertEquals("Test Title", response.getData().getTitle());
    }
}

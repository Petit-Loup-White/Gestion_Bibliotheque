package com.bibliotheque.emprunteur.service;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import com.bibliotheque.emprunteur.entity.Emprunteur;
import com.bibliotheque.emprunteur.exception.DuplicateEmprunteurException;
import com.bibliotheque.emprunteur.exception.EmprunteurNotFoundException;
import com.bibliotheque.emprunteur.repository.EmprunteurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmprunteurServiceTest {

    @Mock
    private EmprunteurRepository emprunteurRepository;

    @InjectMocks
    private EmprunteurService emprunteurService;

    @Test
    void saveShouldThrowWhenEmailAlreadyExists() {
        EmprunteurDTO dto = EmprunteurDTO.builder()
                .nom("Alice")
                .email("alice@mail.com")
                .telephone("670000000")
                .build();

        when(emprunteurRepository.existsByEmail("alice@mail.com")).thenReturn(true);

        assertThrows(DuplicateEmprunteurException.class, () -> emprunteurService.save(dto));
        verify(emprunteurRepository, never()).save(any());
    }

    @Test
    void findByIdOrThrowShouldReturnBorrowerWhenItExists() {
        Emprunteur emprunteur = Emprunteur.builder()
                .id(1L)
                .nom("Alice")
                .email("alice@mail.com")
                .telephone("670000000")
                .build();

        when(emprunteurRepository.findById(1L)).thenReturn(Optional.of(emprunteur));

        EmprunteurDTO result = emprunteurService.findByIdOrThrow(1L);

        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getNom());
    }

    @Test
    void findByIdOrThrowShouldThrowWhenBorrowerDoesNotExist() {
        when(emprunteurRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EmprunteurNotFoundException.class, () -> emprunteurService.findByIdOrThrow(99L));
    }

    @Test
    void deleteShouldThrowWhenBorrowerDoesNotExist() {
        when(emprunteurRepository.existsById(5L)).thenReturn(false);

        assertThrows(EmprunteurNotFoundException.class, () -> emprunteurService.delete(5L));
        verify(emprunteurRepository, never()).deleteById(5L);
    }

    @Test
    void deleteShouldCallRepositoryWhenBorrowerExists() {
        when(emprunteurRepository.existsById(5L)).thenReturn(true);

        emprunteurService.delete(5L);

        verify(emprunteurRepository).deleteById(5L);
    }
}

package com.bibliotheque.emprunteur.controller;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import com.bibliotheque.emprunteur.exception.DuplicateEmprunteurException;
import com.bibliotheque.emprunteur.exception.EmprunteurNotFoundException;
import com.bibliotheque.emprunteur.exception.GlobalExceptionHandler;
import com.bibliotheque.emprunteur.service.EmprunteurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmprunteurController.class)
@Import(GlobalExceptionHandler.class)
class EmprunteurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmprunteurService emprunteurService;

    @Test
    void getByIdShouldReturnNotFoundWhenServiceThrows() throws Exception {
        when(emprunteurService.findByIdOrThrow(3L)).thenThrow(new EmprunteurNotFoundException(3L));

        mockMvc.perform(get("/api/emprunteurs/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Aucun emprunteur trouve avec l'id : 3"));
    }

    @Test
    void createShouldReturnCreatedWhenPayloadIsValid() throws Exception {
        EmprunteurDTO input = EmprunteurDTO.builder()
                .nom("Alice")
                .email("alice@mail.com")
                .telephone("670000000")
                .build();

        EmprunteurDTO saved = EmprunteurDTO.builder()
                .id(1L)
                .nom("Alice")
                .email("alice@mail.com")
                .telephone("670000000")
                .build();

        when(emprunteurService.save(any(EmprunteurDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/emprunteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("alice@mail.com"));
    }

    @Test
    void createShouldReturnBadRequestWhenPayloadIsInvalid() throws Exception {
        EmprunteurDTO input = EmprunteurDTO.builder()
                .nom("")
                .email("email-invalide")
                .telephone("12")
                .build();

        mockMvc.perform(post("/api/emprunteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Les donnees envoyees sont invalides"))
                .andExpect(jsonPath("$.details.nom").exists())
                .andExpect(jsonPath("$.details.email").exists())
                .andExpect(jsonPath("$.details.telephone").exists());
    }

    @Test
    void createShouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        EmprunteurDTO input = EmprunteurDTO.builder()
                .nom("Alice")
                .email("alice@mail.com")
                .telephone("670000000")
                .build();

        when(emprunteurService.save(any(EmprunteurDTO.class)))
                .thenThrow(new DuplicateEmprunteurException("alice@mail.com"));

        mockMvc.perform(post("/api/emprunteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Un emprunteur existe deja avec l'email : alice@mail.com"));
    }

    @Test
    void deleteShouldReturnNoContentWhenBorrowerExists() throws Exception {
        mockMvc.perform(delete("/api/emprunteurs/7"))
                .andExpect(status().isNoContent());

        verify(emprunteurService).delete(7L);
    }

    @Test
    void deleteShouldReturnNotFoundWhenBorrowerDoesNotExist() throws Exception {
        doThrow(new EmprunteurNotFoundException(7L)).when(emprunteurService).delete(7L);

        mockMvc.perform(delete("/api/emprunteurs/7"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}

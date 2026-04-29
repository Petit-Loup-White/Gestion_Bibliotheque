package com.example;

import com.example.controller.LivreController;
import com.example.dto.LivreDTO;
import com.example.exception.LivreNotFoundException;
import com.example.service.LivreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivreController.class)
class ServiceLivreApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LivreService livreService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getAllLivres_shouldReturnList() throws Exception {
		LivreDTO livre1 = LivreDTO.builder().id(1L).titre("1984").auteur("Orwell").isbn("123").disponible(true).build();
		LivreDTO livre2 = LivreDTO.builder().id(2L).titre("Dune").auteur("Herbert").isbn("456").disponible(false).build();
		List<LivreDTO> livres = Arrays.asList(livre1, livre2);

		when(livreService.findAll()).thenReturn(livres);

		mockMvc.perform(get("/api/livres"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].titre", is("1984")));
	}

	@Test
	void getById_shouldReturnLivre() throws Exception {
		LivreDTO livre = LivreDTO.builder().id(1L).titre("1984").auteur("Orwell").isbn("123").disponible(true).build();
		when(livreService.findById(1L)).thenReturn(livre);

		mockMvc.perform(get("/api/livres/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.titre", is("1984")));
	}

	@Test
	void getById_notFound_shouldReturn404() throws Exception {
		when(livreService.findById(99L)).thenThrow(new LivreNotFoundException("Livre avec l'ID 99 non trouvé"));

		mockMvc.perform(get("/api/livres/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", containsString("99")));
	}

	@Test
	void createLivre_shouldReturnCreated() throws Exception {
		LivreDTO request = LivreDTO.builder().titre("Nouveau").auteur("Auteur").isbn("789").build();
		LivreDTO saved = LivreDTO.builder().id(3L).titre("Nouveau").auteur("Auteur").isbn("789").disponible(true).build();

		when(livreService.save(any(LivreDTO.class))).thenReturn(saved);

		mockMvc.perform(post("/api/livres")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())  // ou 201 si configuré
				.andExpect(jsonPath("$.id", is(3)))
				.andExpect(jsonPath("$.disponible", is(true)));
	}

	@Test
	void deleteLivre_shouldReturnOk() throws Exception {
		doNothing().when(livreService).delete(1L);

		mockMvc.perform(delete("/api/livres/1"))
				.andExpect(status().isOk());
	}

	@Test
	void updateDisponibilite_shouldReturnUpdated() throws Exception {
		LivreDTO updated = LivreDTO.builder().id(1L).titre("1984").auteur("Orwell").isbn("123").disponible(false).build();
		when(livreService.updateDisponibilite(1L, false)).thenReturn(updated);

		mockMvc.perform(put("/api/livres/1/disponibilite")
						.param("disponible", "false"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.disponible", is(false)));
	}
}
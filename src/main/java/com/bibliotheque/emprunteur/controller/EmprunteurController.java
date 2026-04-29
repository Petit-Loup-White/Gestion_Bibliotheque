package com.bibliotheque.emprunteur.controller;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import jakarta.validation.Valid;
import com.bibliotheque.emprunteur.service.EmprunteurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emprunteurs")
public class EmprunteurController {

    private final EmprunteurService emprunteurService;

    public EmprunteurController(EmprunteurService emprunteurService) {
        this.emprunteurService = emprunteurService;
    }

    /**
     * GET /api/emprunteurs - Liste tous les emprunteurs.
     */
    @GetMapping
    public ResponseEntity<List<EmprunteurDTO>> getAllEmprunteurs() {
        return ResponseEntity.ok(emprunteurService.findAll());
    }

    /**
     * GET /api/emprunteurs/{id} - Retourne un emprunteur par identifiant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmprunteurDTO> getEmprunteurById(@PathVariable Long id) {
        return ResponseEntity.ok(emprunteurService.findByIdOrThrow(id));
    }

    /**
     * POST /api/emprunteurs - Cree un nouvel emprunteur.
     */
    @PostMapping
    public ResponseEntity<EmprunteurDTO> createEmprunteur(@Valid @RequestBody EmprunteurDTO emprunteurDTO) {
        EmprunteurDTO saved = emprunteurService.save(emprunteurDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * DELETE /api/emprunteurs/{id} - Supprime un emprunteur.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmprunteur(@PathVariable Long id) {
        emprunteurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.bibliotheque.emprunteur.controller;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import com.bibliotheque.emprunteur.service.EmprunteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunteurs")
@RequiredArgsConstructor
public class EmprunteurController {

    private final EmprunteurService emprunteurService;

    /**
     * GET /api/emprunteurs - Liste tous les emprunteurs
     */
    @GetMapping
    public ResponseEntity<List<EmprunteurDTO>> getAllEmprunteurs() {
        return ResponseEntity.ok(emprunteurService.findAll());
    }

    /**
     * GET /api/emprunteurs/{id} - Retourne un emprunteur par id
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmprunteurDTO> getEmprunteurById(@PathVariable Long id) {
        return emprunteurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/emprunteurs - Crée un nouvel emprunteur
     */
    @PostMapping
    public ResponseEntity<EmprunteurDTO> createEmprunteur(@RequestBody EmprunteurDTO emprunteurDTO) {
        EmprunteurDTO saved = emprunteurService.save(emprunteurDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * DELETE /api/emprunteurs/{id} - Supprime un emprunteur
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmprunteur(@PathVariable Long id) {
        return emprunteurService.findById(id)
                .map(e -> {
                    emprunteurService.delete(id);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

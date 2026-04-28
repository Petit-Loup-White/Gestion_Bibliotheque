package com.example.controller;

import com.example.dto.LivreDTO;
import com.example.service.LivreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
@RequiredArgsConstructor
@Tag(name = "Livres", description = "Endpoints de gestion des livres")
public class LivreController {

    private final LivreService livreService;

    @GetMapping
    @Operation(summary = "Lister tous les livres")
    public List<LivreDTO> getAll() { return livreService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un livre par son ID")
    public LivreDTO getById(@PathVariable @Parameter(description = "ID du livre") Long id) {
        return livreService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Ajouter un nouveau livre")
    public LivreDTO create(@RequestBody @Parameter(description = "Données du livre") LivreDTO dto) {
        return livreService.save(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre")
    public void delete(@PathVariable Long id) { livreService.delete(id); }

    @PutMapping("/{id}/disponibilite")
    @Operation(summary = "Modifier la disponibilité d'un livre")
    public LivreDTO updateDisponibilite(@PathVariable Long id,
                                        @RequestParam @Parameter(description = "true si disponible") boolean disponible) {
        return livreService.updateDisponibilite(id, disponible);
    }
}
package com.bibliotheque.emprunteur.controller;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Emprunteurs", description = "API de gestion des emprunteurs")
public class EmprunteurController {

    private final EmprunteurService emprunteurService;

    public EmprunteurController(EmprunteurService emprunteurService) {
        this.emprunteurService = emprunteurService;
    }

    /**
     * GET /api/emprunteurs - Liste tous les emprunteurs.
     */
    @GetMapping
    @Operation(summary = "Lister les emprunteurs", description = "Retourne la liste complete des emprunteurs")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des emprunteurs recuperee avec succes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmprunteurDTO.class)))
    )
    public ResponseEntity<List<EmprunteurDTO>> getAllEmprunteurs() {
        return ResponseEntity.ok(emprunteurService.findAll());
    }

    /**
     * GET /api/emprunteurs/{id} - Retourne un emprunteur par identifiant.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un emprunteur", description = "Retourne un emprunteur a partir de son identifiant")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Emprunteur trouve",
                    content = @Content(schema = @Schema(implementation = EmprunteurDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Emprunteur introuvable")
    })
    public ResponseEntity<EmprunteurDTO> getEmprunteurById(@PathVariable Long id) {
        return ResponseEntity.ok(emprunteurService.findByIdOrThrow(id));
    }

    /**
     * POST /api/emprunteurs - Cree un nouvel emprunteur.
     */
    @PostMapping
    @Operation(summary = "Creer un emprunteur", description = "Ajoute un nouvel emprunteur dans le systeme")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Emprunteur cree avec succes",
                    content = @Content(schema = @Schema(implementation = EmprunteurDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "409", description = "Email deja utilise")
    })
    public ResponseEntity<EmprunteurDTO> createEmprunteur(@Valid @RequestBody EmprunteurDTO emprunteurDTO) {
        EmprunteurDTO saved = emprunteurService.save(emprunteurDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * DELETE /api/emprunteurs/{id} - Supprime un emprunteur.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un emprunteur", description = "Supprime un emprunteur a partir de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Emprunteur supprime avec succes"),
            @ApiResponse(responseCode = "404", description = "Emprunteur introuvable")
    })
    public ResponseEntity<Void> deleteEmprunteur(@PathVariable Long id) {
        emprunteurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

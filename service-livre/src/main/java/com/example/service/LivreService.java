package com.example.service;


import com.example.dto.LivreDTO;
import com.example.exception.LivreNotFoundException;
import com.example.model.Livre;
import com.example.repository.LivreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivreService {

    private final LivreRepository livreRepository;

    public List<LivreDTO> findAll() {
        return livreRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LivreDTO findById(Long id) {
        return livreRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new LivreNotFoundException("Livre avec l'ID " + id + " non trouvé"));
    }

    public LivreDTO save(LivreDTO dto) {
        Livre livre = toEntity(dto);
        livre.setDisponible(true); // par défaut disponible
        Livre saved = livreRepository.save(livre);
        return toDTO(saved);
    }

    public void delete(Long id) {
        livreRepository.deleteById(id);
    }

    public LivreDTO updateDisponibilite(Long id, boolean disponible) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new LivreNotFoundException("Livre avec l'ID " + id + " non trouvé"));
        livre.setDisponible(disponible);
        return toDTO(livreRepository.save(livre));
    }

    private LivreDTO toDTO(Livre livre) {
        return LivreDTO.builder()
                .id(livre.getId())
                .titre(livre.getTitre())
                .auteur(livre.getAuteur())
                .isbn(livre.getIsbn())
                .disponible(livre.getDisponible())
                .build();
    }

    private Livre toEntity(LivreDTO dto) {
        return Livre.builder()
                .id(dto.getId())
                .titre(dto.getTitre())
                .auteur(dto.getAuteur())
                .isbn(dto.getIsbn())
                .disponible(dto.getDisponible() != null ? dto.getDisponible() : true)
                .build();
    }
}
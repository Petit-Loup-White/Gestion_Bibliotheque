package com.bibliotheque.emprunteur.service;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import com.bibliotheque.emprunteur.entity.Emprunteur;
import com.bibliotheque.emprunteur.repository.EmprunteurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmprunteurService {

    private final EmprunteurRepository emprunteurRepository;

    public List<EmprunteurDTO> findAll() {
        return emprunteurRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmprunteurDTO> findById(Long id) {
        return emprunteurRepository.findById(id).map(this::toDTO);
    }

    public EmprunteurDTO save(EmprunteurDTO dto) {
        Emprunteur emprunteur = toEntity(dto);
        return toDTO(emprunteurRepository.save(emprunteur));
    }

    public void delete(Long id) {
        emprunteurRepository.deleteById(id);
    }

    // ---- Conversion Entité <-> DTO ----

    private EmprunteurDTO toDTO(Emprunteur e) {
        return EmprunteurDTO.builder()
                .id(e.getId())
                .nom(e.getNom())
                .email(e.getEmail())
                .telephone(e.getTelephone())
                .build();
    }

    private Emprunteur toEntity(EmprunteurDTO dto) {
        return Emprunteur.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .build();
    }
}

package com.bibliotheque.emprunteur.service;

import com.bibliotheque.emprunteur.dto.EmprunteurDTO;
import com.bibliotheque.emprunteur.entity.Emprunteur;
import com.bibliotheque.emprunteur.exception.DuplicateEmprunteurException;
import com.bibliotheque.emprunteur.exception.EmprunteurNotFoundException;
import com.bibliotheque.emprunteur.repository.EmprunteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmprunteurService {

    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository = emprunteurRepository;
    }

    public List<EmprunteurDTO> findAll() {
        return emprunteurRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<EmprunteurDTO> findById(Long id) {
        return emprunteurRepository.findById(id).map(this::toDTO);
    }

    public EmprunteurDTO findByIdOrThrow(Long id) {
        return emprunteurRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EmprunteurNotFoundException(id));
    }

    public EmprunteurDTO save(EmprunteurDTO dto) {
        if (emprunteurRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmprunteurException(dto.getEmail());
        }

        Emprunteur emprunteur = toEntity(dto);
        return toDTO(emprunteurRepository.save(emprunteur));
    }

    public void delete(Long id) {
        if (!emprunteurRepository.existsById(id)) {
            throw new EmprunteurNotFoundException(id);
        }

        emprunteurRepository.deleteById(id);
    }

    // Conversion Entite <-> DTO
    private EmprunteurDTO toDTO(Emprunteur emprunteur) {
        return EmprunteurDTO.builder()
                .id(emprunteur.getId())
                .nom(emprunteur.getNom())
                .email(emprunteur.getEmail())
                .telephone(emprunteur.getTelephone())
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

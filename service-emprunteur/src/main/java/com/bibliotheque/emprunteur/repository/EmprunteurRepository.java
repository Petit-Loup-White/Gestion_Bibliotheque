package com.bibliotheque.emprunteur.repository;

import com.bibliotheque.emprunteur.entity.Emprunteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprunteurRepository extends JpaRepository<Emprunteur, Long> {
    boolean existsByEmail(String email);
}

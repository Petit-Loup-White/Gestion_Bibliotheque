package com.bibliotheque.emprunteur.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité JPA représentant un emprunteur de la bibliothèque.
 */
@Entity
@Table(name = "emprunteurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprunteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "telephone")
    private String telephone;
}

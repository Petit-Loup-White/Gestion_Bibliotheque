package com.bibliotheque.emprunteur.dto;

import lombok.*;

/**
 * DTO pour l'emprunteur - exposé dans les réponses REST.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmprunteurDTO {

    private Long id;
    private String nom;
    private String email;
    private String telephone;
}

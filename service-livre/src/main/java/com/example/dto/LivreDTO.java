package com.example.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LivreDTO {
    private Long id;
    private String titre;
    private String auteur;
    private String isbn;
    private Boolean disponible;
}
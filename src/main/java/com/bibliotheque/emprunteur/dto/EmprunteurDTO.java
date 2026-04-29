package com.bibliotheque.emprunteur.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO pour l'emprunteur expose dans les reponses REST.
 */
public class EmprunteurDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    private String email;

    @NotBlank(message = "Le telephone est obligatoire")
    @Pattern(regexp = "^[0-9+() -]{6,20}$", message = "Le telephone doit etre valide")
    private String telephone;

    public EmprunteurDTO() {
    }

    public EmprunteurDTO(Long id, String nom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static class Builder {
        private Long id;
        private String nom;
        private String email;
        private String telephone;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public EmprunteurDTO build() {
            return new EmprunteurDTO(id, nom, email, telephone);
        }
    }
}

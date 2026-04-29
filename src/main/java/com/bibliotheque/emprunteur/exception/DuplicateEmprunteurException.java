package com.bibliotheque.emprunteur.exception;

public class DuplicateEmprunteurException extends RuntimeException {

    public DuplicateEmprunteurException(String email) {
        super("Un emprunteur existe deja avec l'email : " + email);
    }
}

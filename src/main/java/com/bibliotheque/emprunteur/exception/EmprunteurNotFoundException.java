package com.bibliotheque.emprunteur.exception;

public class EmprunteurNotFoundException extends RuntimeException {

    public EmprunteurNotFoundException(Long id) {
        super("Aucun emprunteur trouve avec l'id : " + id);
    }
}

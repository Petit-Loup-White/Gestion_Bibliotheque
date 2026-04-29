package com.bibliotheque.emprunteur.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleNotFoundShouldBuild404Response() {
        ResponseEntity<ApiErrorResponse> response =
                globalExceptionHandler.handleNotFound(new EmprunteurNotFoundException(9L));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Aucun emprunteur trouve avec l'id : 9", response.getBody().message());
    }

    @Test
    void handleDuplicateShouldBuild409Response() {
        ResponseEntity<ApiErrorResponse> response =
                globalExceptionHandler.handleDuplicate(new DuplicateEmprunteurException("alice@mail.com"));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Un emprunteur existe deja avec l'email : alice@mail.com", response.getBody().message());
    }

    @Test
    void handleGenericShouldBuild500Response() {
        ResponseEntity<ApiErrorResponse> response =
                globalExceptionHandler.handleGeneric(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Map.of("cause", "boom"), response.getBody().details());
    }
}

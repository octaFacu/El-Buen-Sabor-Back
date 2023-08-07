package com.example.demo.Entidades.Excepciones;

public class PaginaVaciaException extends RuntimeException {
    public PaginaVaciaException (String message) {
        super(message);
    }
}

package com.example.demo.Entidades.Excepciones;

public class DireccionUsuarioExisteException extends RuntimeException {
    public DireccionUsuarioExisteException(String message) {
        super(message);
    }
}
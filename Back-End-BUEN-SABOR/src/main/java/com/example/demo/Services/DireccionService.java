package com.example.demo.Services;

import com.example.demo.Entidades.Direccion;

import java.util.List;

public interface DireccionService extends GenericService<Direccion, Long> {
    List<Direccion> findByUsuarioId(String usuarioId);
}

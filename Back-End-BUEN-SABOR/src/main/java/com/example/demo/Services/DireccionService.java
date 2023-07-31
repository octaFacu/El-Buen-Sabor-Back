package com.example.demo.Services;

import com.example.demo.Entidades.Direccion;

import java.util.List;

public interface DireccionService extends GenericService<Direccion, Long> {
    List<Direccion> findByUsuarioId(String usuarioId);
    void verificadorDirUsuario(String usuarioId, Direccion direccion) throws Exception;

     Direccion update(Long id, Direccion direccion, String usuarioId) throws Exception;

}

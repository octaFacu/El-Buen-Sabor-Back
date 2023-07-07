package com.example.demo.Services;

import com.example.demo.Entidades.Direccion;

import com.example.demo.Repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpDireccionService extends GenericServiceImpl<Direccion,Long> implements DireccionService {

    @Autowired
    protected DireccionRepository repository;

    @Override
    public List<Direccion> findByUsuarioId(String usuarioId) {
        List<Direccion> direcciones = repository.findByUsuarioId(usuarioId);
        return direcciones;
    }
}

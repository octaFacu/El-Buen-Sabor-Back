package com.example.demo.Services;

import com.example.demo.Entidades.UnidadDeMedida;
import com.example.demo.Repository.UmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpUmService extends GenericServiceImpl<UnidadDeMedida,Long> implements UmService {


    @Autowired
    UmRepository repository;

    @Override
    public List<UnidadDeMedida> findSubUM(Long idUmPadre) throws Exception {
        return repository.findByPadreId(idUmPadre);
    }
}

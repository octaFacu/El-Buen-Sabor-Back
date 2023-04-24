package com.example.demo.Services;

import com.example.demo.Entidades.UnidadDeMedida;
import com.example.genericos.genericos.services.GenericService;

import java.util.List;

public interface UmService extends GenericService<UnidadDeMedida,Long> {

    public List<UnidadDeMedida> findSubUM(Long idUmPadre) throws Exception;

}

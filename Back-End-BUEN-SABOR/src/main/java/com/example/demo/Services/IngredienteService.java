package com.example.demo.Services;

import com.example.demo.Entidades.Ingrediente;
import com.example.genericos.genericos.services.GenericService;

import java.util.List;

public interface IngredienteService extends GenericService<Ingrediente,Long> {

    List<Ingrediente> findByCategoria(Long idCategoria) throws Exception;

}

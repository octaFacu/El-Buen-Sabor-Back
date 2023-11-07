package com.example.demo.Services;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.Wrapper.RequestWrapper;

import java.util.List;

public interface IngredienteService extends GenericService<Ingrediente,Long> {

    List<Ingrediente> findByCategoriaIngrediente(Long idCategoria) throws Exception;
    double findCosto(RequestWrapper ingrediente) throws Exception;

}

package com.example.demo.Services;

import com.example.demo.Entidades.Ingrediente;

import java.util.List;

public interface IngredienteService extends GenericService<Ingrediente,Long> {

    List<Ingrediente> findByCategoriaIngrediente(Long idCategoria) throws Exception;

}

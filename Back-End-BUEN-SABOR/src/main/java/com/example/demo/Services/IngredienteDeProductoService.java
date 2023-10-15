package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Wrapper.RequestWrapper;

public interface IngredienteDeProductoService extends GenericService<IngredientesDeProductos,Long> {

    public void saveIngredientes(RequestWrapper request) throws Exception;
}

package com.example.demo.Services;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.genericos.genericos.services.GenericService;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IngredienteDeProductoService extends GenericService<IngredientesDeProductos,Long> {

    public void saveIngredientes(RequestWrapper request) throws Exception;
}

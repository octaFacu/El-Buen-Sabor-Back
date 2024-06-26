package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Wrapper.RequestWrapper;

import java.util.List;

public interface IngredienteDeProductoService extends GenericService<IngredientesDeProductos,Long> {

    public void saveIngredientes(RequestWrapper request) throws Exception;
    public List<IngredientesDeProductos> getByIngredientes(Long idIngrediente) throws Exception;

    public String UpdateStockIngredientesPedido(int idPedido) throws Exception;
}

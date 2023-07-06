package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.genericos.genericos.services.GenericService;

import java.util.List;

public interface ProductoService extends GenericService<Producto, Long> {

    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception;

    public void saveIngredientes(List<IngredientesDeProductos> ingredientes) throws Exception;

    //public void deleteIngredientes(Long idProducto) throws Exception;
}

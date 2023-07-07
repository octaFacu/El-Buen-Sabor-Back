package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;

import java.util.List;

public interface ProductoService extends GenericService<Producto, Long> {

    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception;
}

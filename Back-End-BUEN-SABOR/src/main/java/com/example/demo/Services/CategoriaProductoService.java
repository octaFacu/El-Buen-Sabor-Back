package com.example.demo.Services;

import com.example.demo.Entidades.CategoriaProducto;

import java.util.List;

public interface CategoriaProductoService  extends GenericService<CategoriaProducto, Long> {

    List<CategoriaProducto> getAllActivo() throws Exception;

}

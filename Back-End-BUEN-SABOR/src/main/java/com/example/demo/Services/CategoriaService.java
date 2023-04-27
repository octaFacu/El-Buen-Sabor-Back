package com.example.demo.Services;

import com.example.demo.Entidades.Categoria;
import com.example.genericos.genericos.services.GenericService;

import java.util.List;

public interface CategoriaService extends GenericService<Categoria,Long> {

    public List<Categoria> findSubCategorias(Long idCategoriaPadre) throws Exception;

    public List<Categoria> findCategoriaPadres() throws Exception;

}

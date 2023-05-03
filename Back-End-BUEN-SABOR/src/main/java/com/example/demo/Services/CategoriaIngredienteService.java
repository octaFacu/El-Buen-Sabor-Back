package com.example.demo.Services;

import com.example.demo.Entidades.CategoriaIngrediente;
import com.example.genericos.genericos.services.GenericService;

import java.util.List;

public interface CategoriaIngredienteService extends GenericService<CategoriaIngrediente,Long> {

    public List<CategoriaIngrediente> findSubCategorias(Long idCategoriaPadre) throws Exception;

    public List<CategoriaIngrediente> findCategoriaPadres() throws Exception;

    public List<CategoriaIngrediente> findCategoriaPadresConHijos() throws Exception;

}

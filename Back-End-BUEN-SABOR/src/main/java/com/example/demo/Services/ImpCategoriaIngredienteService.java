package com.example.demo.Services;

import com.example.demo.Entidades.CategoriaIngrediente;
import com.example.demo.Repository.CategoriaIngredienteRepository;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpCategoriaIngredienteService extends GenericServiceImpl<CategoriaIngrediente,Long> implements CategoriaIngredienteService {

    @Autowired
    private CategoriaIngredienteRepository repository;

    @Override
    public List<CategoriaIngrediente> findSubCategorias(Long idCategoriaPadre) throws Exception {
        try {
            List<CategoriaIngrediente> subcategorias = repository.findByCategoriaIngredientePadreId(idCategoriaPadre);
            return subcategorias;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<CategoriaIngrediente> findCategoriaPadres() throws Exception {
        try {
            List<CategoriaIngrediente> categoriasPadres = repository.findPadres();
            return categoriasPadres;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<CategoriaIngrediente> findCategoriaPadresConHijos() throws Exception {
        try {
            List<CategoriaIngrediente> categoriasPadres = repository.findPadresConHijos();
            return categoriasPadres;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}

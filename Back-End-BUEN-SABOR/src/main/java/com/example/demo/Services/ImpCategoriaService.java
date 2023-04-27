package com.example.demo.Services;

import com.example.demo.Entidades.Categoria;
import com.example.demo.Repository.CategoriaRepository;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpCategoriaService extends GenericServiceImpl<Categoria,Long> implements CategoriaService{

    @Autowired
    private CategoriaRepository repository;

    @Override
    public List<Categoria> findSubCategorias(Long idCategoriaPadre) throws Exception {
        try {
            List<Categoria> subcategorioas = repository.findByCategoriaPadreId(idCategoriaPadre);
            return subcategorioas;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Categoria> findCategoriaPadres() throws Exception {
        try {
            List<Categoria> categoriasPadres = repository.findPadres();
            return categoriasPadres;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}

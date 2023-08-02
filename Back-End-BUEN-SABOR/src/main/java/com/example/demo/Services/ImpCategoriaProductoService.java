package com.example.demo.Services;

import com.example.demo.Entidades.CategoriaProducto;
import com.example.demo.Repository.CategoriaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpCategoriaProductoService extends GenericServiceImpl<CategoriaProducto,Long> implements CategoriaProductoService{

    @Autowired
    protected CategoriaProductoRepository repository;

    @Override
    public List<CategoriaProducto> getAllActivo() throws Exception {
        List<CategoriaProducto> categorias = repository.findAllActivo();
        return categorias;
    }
}

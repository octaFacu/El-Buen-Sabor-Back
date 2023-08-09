package com.example.demo.Services;


import com.example.demo.Entidades.CategoriaIngrediente;
import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Repository.CategoriaIngredienteRepository;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpProductoService extends GenericServiceImpl<Producto,Long> implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Override
    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception {
        try {
            List<IngredientesDeProductos> ingredientes = repository.findByIdProducto(idProducto);
            return ingredientes;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Filtro por nombre de producto
    @Override
    public List<Producto> filtro(String filter) throws Exception {
        try{
            List<Producto> entities = repository.filtro(filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Filtro paginado por nombre de producto
    @Override
    public Page<Producto> filtroPaginado(Pageable pageable, String filter) throws Exception {
        try{
            Page<Producto> entities = repository.filtroPaginado(pageable, filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Filtro paginado por CATEGORIA de producto
    @Override
    public Page<Producto> filtroCategoriaPaginado(Pageable pageable, Long filter) throws Exception {
        try{
            Page<Producto> entities = repository.filtroCategoriaPaginado(pageable, filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Filtro por CATEGORIA de producto
    @Override
    public List<Producto> filtroCategoria(Long filter) throws Exception {
        try{
            List<Producto> entities = repository.filtroCategoria(filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}

package com.example.demo.Services;


import com.example.demo.Entidades.CategoriaIngrediente;
import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Repository.CategoriaIngredienteRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.genericos.genericos.services.GenericServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpProductoService extends GenericServiceImpl<Producto,Long> implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Override
    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception {
        try {
            List<IngredientesDeProductos> ingredientes = repository.findByIdProducto(idProducto);

            if (ingredientes == null) {
                // Inicializar ingredientes como una lista vacia para devolverlo
                ingredientes = new ArrayList<>();
            }

            return ingredientes;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void saveIngredientes(List<IngredientesDeProductos> ingredientes) throws Exception {
        try {
            for (IngredientesDeProductos ingrediente : ingredientes) {
                repository.insertIngrediente(ingrediente.getCantidad(), ingrediente.getUnidadmedida().getId(),
                        ingrediente.getIngrediente().getId(), ingrediente.getProducto().getId());
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*@Override
    @Modifying
    public void deleteIngredientes(Long idProducto) throws Exception {
        try {
            repository.deleteByIdProducto(idProducto);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }*/
}

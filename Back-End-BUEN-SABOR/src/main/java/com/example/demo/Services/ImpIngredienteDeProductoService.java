package com.example.demo.Services;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Repository.IngredienteDeProductoRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.genericos.genericos.services.GenericServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpIngredienteDeProductoService  extends GenericServiceImpl<IngredientesDeProductos,Long> implements IngredienteDeProductoService{

    @Autowired
    private IngredienteDeProductoRepository repository;

    @Override
    @Transactional
    public void saveIngredientes(RequestWrapper request) throws Exception {
        try {


                    repository.insertIngrediente(request.getCantidad(), request.getIdMedida(),
                            request.getIdIngrediente(), request.getIdProducto());



        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}

package com.example.demo.Services;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Repository.IngredienteRepository;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpIngredienteService extends GenericServiceImpl<Ingrediente,Long> implements IngredienteService {

    @Autowired
     private IngredienteRepository repositorio;

    @Override
    public List<Ingrediente> findByCategoria(Long idCategoria) throws Exception {
        try{
            List<Ingrediente> ingredientesPorCategoria = repositorio.findByCategoria(idCategoria);
            return  ingredientesPorCategoria;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

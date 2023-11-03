package com.example.demo.Services;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpFavoritoService extends GenericServiceImpl<Favorito,Long> implements FavoritoService{

    @Autowired
    FavoritoRepository repository;
    @Override
    public Page<ProyeccionProductoFavorito> findbyId_cliente(long id_cliente, Pageable pageable) throws Exception {
        try{

            return repository.findbyId_cliente(id_cliente, pageable);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

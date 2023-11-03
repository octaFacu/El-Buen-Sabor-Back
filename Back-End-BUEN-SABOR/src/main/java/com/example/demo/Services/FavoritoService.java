package com.example.demo.Services;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoritoService extends GenericService<Favorito,Long> {
    Page<ProyeccionProductoFavorito> findbyId_cliente(long id_cliente, Pageable pageable) throws Exception;
}

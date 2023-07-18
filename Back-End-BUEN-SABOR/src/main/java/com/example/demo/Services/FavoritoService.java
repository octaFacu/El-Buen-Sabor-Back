package com.example.demo.Services;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;

import java.util.List;

public interface FavoritoService extends GenericService<Favorito,Long> {
    List<ProyeccionProductoFavorito> findbyId_cliente(long id_cliente) throws Exception;
}

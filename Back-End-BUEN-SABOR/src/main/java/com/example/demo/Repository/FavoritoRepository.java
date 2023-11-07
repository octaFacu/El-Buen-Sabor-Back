package com.example.demo.Repository;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends GenericRepository<Favorito, Long> {

    @Query(value = "SELECT favorito.id, producto.denominacion, producto.imagen FROM favorito LEFT JOIN producto ON producto.id = favorito.id_producto WHERE favorito.id_cliente = :id_cliente",nativeQuery = true)
    Page<ProyeccionProductoFavorito> findbyId_cliente(@Param("id_cliente") long id_cliente, Pageable pageable);

    @Query("SELECT f FROM Favorito f WHERE f.cliente.id = :id_cliente")
    List<Favorito> findAllByClienteId(@Param("id_cliente") long id_cliente);

    @Query("SELECT f FROM Favorito f JOIN f.cliente c WHERE f.producto.id = :id_producto AND c.usuario.id = :id_cliente")
    Favorito findFavoritoByClienteAndProductoId(@Param("id_cliente") String id_cliente, @Param("id_producto") long id_producto);

}


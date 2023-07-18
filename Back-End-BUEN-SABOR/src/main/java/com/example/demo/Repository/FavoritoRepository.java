package com.example.demo.Repository;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends GenericRepository<Favorito, Long> {

    @Query(value = "SELECT favorito.id, producto.denominacion, producto.imagen FROM producto INNER JOIN favorito ON producto.id = favorito.id WHERE favorito.id_cliente = :id_cliente",nativeQuery = true)
    List<ProyeccionProductoFavorito> findbyId_cliente(@Param("id_cliente") long id_cliente);

}


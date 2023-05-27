package com.example.demo.Repository;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.UnidadDeMedida;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends GenericRepository<Producto, Long> {
    @Query("SELECT ing FROM IngredientesDeProductos ing WHERE ing.producto.id = :idProducto")
    List<IngredientesDeProductos> findByIdProducto(@Param("idProducto") Long idProducto);

    //It must be called before doing the update
    @Query("DELETE FROM IngredientesDeProductos ing WHERE ing.producto.id = :idProducto")
    void deleteByIdProducto (@Param("idProducto") Long idProducto);



    @Query("INSERT INTO IngredientesDeProductos (cantidad, unidadmedida, ingrediente, producto) " +
            "VALUES (:cantidad, :unidadMedidaId, :ingredienteId, :productoId)")
    void insertIngrediente(@Param("cantidad") Double cant,
                           @Param("unidadMedidaId") Long medidaId,
                           @Param("ingredienteId") Long ingredienteId,
                           @Param("productoId") Long productoId);
}

package com.example.demo.Repository;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.UnidadDeMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends GenericRepository<Producto, Long> {
    @Query("SELECT ing FROM IngredientesDeProductos ing WHERE ing.producto.id = :idProducto")
    List<IngredientesDeProductos> findByIdProducto(@Param("idProducto") Long idProducto);

    //Filtro por NOMBRE de producto y por ACTIVO para LANDING
    @Query("SELECT l FROM Producto l WHERE l.denominacion LIKE %:filter% AND l.activo = true")
    List<Producto> filtro(@Param("filter") String filter);

    //Filtro paginado por NOMBRE de producto y por ACTIVO para LANDING
    @Query("SELECT l FROM Producto l WHERE l.denominacion LIKE %:filter% AND l.activo = true")
    Page<Producto> filtroPaginado(Pageable peageable, @Param("filter") String filter);

    //Filtro paginado por CATEGORIA y por ACTIVO de producto para LANDING
    @Query("SELECT l FROM Producto l INNER JOIN l.categoriaProducto c WHERE c.id = :filter AND l.activo = true")
    Page<Producto> filtroCategoriaPaginado(Pageable peageable, @Param("filter") Long filter);

    //Filtro por CATEGORIA de producto y por ACTIVO para LANDING
    @Query("SELECT l FROM Producto l INNER JOIN l.categoriaProducto c WHERE c.id = :filter AND l.activo = true")
    List<Producto> filtroCategoria(@Param("filter") Long filter);

}

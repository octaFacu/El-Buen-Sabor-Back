package com.example.demo.Repository;

import com.example.demo.Entidades.*;
import com.example.demo.Entidades.Proyecciones.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ProductoRepository extends GenericRepository<Producto, Long> {
    @Query("SELECT ing FROM IngredientesDeProductos ing WHERE ing.producto.id = :idProducto")
    List<IngredientesDeProductos> findByIdProducto(@Param("idProducto") Long idProducto);

    @Modifying
    @Query("UPDATE Producto p SET " +
            "p.denominacion = :denominacion, " +
            "p.esManufacturado = :esManufacturado, " +
            "p.tiempoCocina = :tiempoCocina, " +
            "p.descripcion = :descripcion, " +
            "p.receta = :receta, " +
            "p.costoTotal = :costoTotal, " +
            "p.imagen = :imagen, " +
            "p.precioTotal = :precioTotal, " +
            "p.activo = :activo, " +
            "p.stock = :stock, " +
            "p.categoriaProducto = :categoriaProducto " +
            "WHERE p.id = :id")
    int updateProductoWithImage(
            @Param("id") Long id,
            @Param("denominacion") String denominacion,
            @Param("esManufacturado") Boolean esManufacturado,
            @Param("tiempoCocina") String tiempoCocina,
            @Param("descripcion") String descripcion,
            @Param("receta") String receta,
            @Param("costoTotal") Double costoTotal,
            @Param("imagen") String imagen,
            @Param("precioTotal") Double precioTotal,
            @Param("activo") Boolean activo,
            @Param("stock") int stock,
            @Param("categoriaProducto") CategoriaProducto categoriaProducto);

    @Modifying
    @Query("UPDATE Producto p SET " +
            "p.denominacion = :denominacion, " +
            "p.esManufacturado = :esManufacturado, " +
            "p.tiempoCocina = :tiempoCocina, " +
            "p.descripcion = :descripcion, " +
            "p.receta = :receta, " +
            "p.costoTotal = :costoTotal, " +
            "p.precioTotal = :precioTotal, " +
            "p.activo = :activo, " +
            "p.stock = :stock, " +
            "p.categoriaProducto = :categoriaProducto " +
            "WHERE p.id = :id")
    int updateProductoWithoutImage(
            @Param("id") Long id,
            @Param("denominacion") String denominacion,
            @Param("esManufacturado") Boolean esManufacturado,
            @Param("tiempoCocina") String tiempoCocina,
            @Param("descripcion") String descripcion,
            @Param("receta") String receta,
            @Param("costoTotal") Double costoTotal,
            @Param("precioTotal") Double precioTotal,
            @Param("activo") Boolean activo,
            @Param("stock") int stock,
            @Param("categoriaProducto") CategoriaProducto categoriaProducto);


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


    @Query(value = "CALL generarRankingProductos(:fechaInicio, :fechaFin, :direccionOrden, FALSE)", nativeQuery = true)
    List<ProyeccionRankingProductos> rankingProductosComida(@Param("fechaInicio") Date fechaInicio,
                                                       @Param("fechaFin") Date fechaFin,
                                                       @Param("direccionOrden") String campoDireccion);

    @Query(value = "CALL generarRankingProductos(:fechaInicio, :fechaFin, :direccionOrden, TRUE)", nativeQuery = true)
    List<ProyeccionRankingProductos> rankingProductosBebidas(@Param("fechaInicio") Date fechaInicio,
                                                            @Param("fechaFin") Date fechaFin,
                                                            @Param("direccionOrden") String campoDireccion);

    @Query(value = "SELECT producto.precio_total, producto.denominacion, COUNT(pedido_has_producto.producto_id) AS cantidad_pedida " +
            "FROM producto INNER JOIN pedido_has_producto ON producto.id = pedido_has_producto.producto_id " +
            "INNER JOIN pedido ON pedido.id = pedido_has_producto.pedido_id " +
            "WHERE (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (DATE(pedido.fecha_pedido) BETWEEN :fechaInicio AND :fechaFin) " +
            "GROUP BY pedido_has_producto.producto_id;", nativeQuery = true)
    List<ProyeccionGraficoInfomeGanancias> graficoInformeGanancias(@Param("fechaInicio") Date fechaInicio,
                                                                   @Param("fechaFin") Date fechaFin);

    @Query(value = "SELECT SUM(IF((:fechaInicio IS NULL AND :fechaFin IS NULL) OR (pedido.fecha_pedido IS NOT NULL AND pedido.fecha_pedido " +
            "BETWEEN :fechaInicio AND :fechaFin)," +
            "IFNULL(pedido_has_producto.cantidad, 0) * (producto.precio_total - producto.costo_total), 0)) AS ganancia, " +
            "producto.denominacion FROM producto LEFT JOIN pedido_has_producto ON producto.id = pedido_has_producto.producto_id" +
            " LEFT JOIN pedido ON pedido_has_producto.pedido_id = pedido.id " +
            "GROUP BY producto.denominacion;"
            , nativeQuery = true)
    List<ProyeccionInformeRentabilidad> graficoRentabilidad(@Param("fechaInicio") Date fechaInicio,
                                                            @Param("fechaFin") Date fechaFin);

    @Query(value = "SELECT DATE(pedido.fecha_pedido) AS fecha, " +
            " SUM(producto.precio_total - producto.costo_total) AS ganancia" +
            " FROM producto " +
            " INNER JOIN pedido_has_producto ON producto.id = pedido_has_producto.producto_id " +
            " INNER JOIN pedido ON pedido.id = pedido_has_producto.pedido_id " +
            " GROUP BY fecha " +
            " ORDER BY fecha;", nativeQuery = true)
    List<ProyeccionGananciaMes> graficoGananciasDia();

    @Query(value = "SELECT STR_TO_DATE(CONCAT(DATE_FORMAT(pedido.fecha_pedido, '%Y-%m'), '-01'), '%Y-%m-%d') AS fecha, " +
            "SUM(producto.precio_total - producto.costo_total) AS ganancia " +
            "FROM producto INNER JOIN pedido_has_producto ON producto.id = pedido_has_producto.producto_id " +
            "INNER JOIN pedido ON pedido.id = pedido_has_producto.pedido_id " +
            "GROUP BY fecha ORDER BY fecha;",
            nativeQuery = true)
    List<ProyeccionGananciaMes> graficoGanancias();

    @Query(value = "SELECT DATE(CONCAT(YEAR(pedido.fecha_pedido), '-01-01')) AS fecha," +
            " SUM(producto.precio_total - producto.costo_total) AS ganancia " +
            " FROM producto  INNER JOIN pedido_has_producto ON producto.id = pedido_has_producto.producto_id " +
            " INNER JOIN pedido ON pedido.id = pedido_has_producto.pedido_id " +
            " GROUP BY fecha ORDER BY fecha;", nativeQuery = true)
    List<ProyeccionGananciaMes> graficoGananciasAnio();

    @Query(value = "SELECT p.* " +
            "FROM producto p " +
            "JOIN " +
            "(SELECT producto_id, COUNT(producto_id) AS repeticiones " +
            "FROM pedido_has_producto " +
            "GROUP BY producto_id " +
            "ORDER BY repeticiones DESC " +
            "LIMIT 3) " +
            "subconsulta ON p.id = subconsulta.producto_id;"
            , nativeQuery = true)
    List<Producto> buscarMasVendidos();


}

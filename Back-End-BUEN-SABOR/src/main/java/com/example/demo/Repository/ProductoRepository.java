package com.example.demo.Repository;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.*;
import com.example.demo.Entidades.UnidadDeMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ProductoRepository extends GenericRepository<Producto, Long> {
    @Query("SELECT ing FROM IngredientesDeProductos ing WHERE ing.producto.id = :idProducto")
    List<IngredientesDeProductos> findByIdProducto(@Param("idProducto") Long idProducto);

    //Filtro paginado por nombre de producto
    @Query("SELECT l FROM Producto l WHERE l.denominacion LIKE %:filter%")
    Page<Producto> filtroPaginado(Pageable peageable, @Param("filter") String filter);

    //Filtro paginado por CATEGORIA de producto
    @Query("SELECT l FROM Producto l INNER JOIN l.categoriaProducto c WHERE c.id = :filter")
    Page<Producto> filtroCategoriaPaginado(Pageable peageable, @Param("filter") Long filter);

    //Filtro por CATEGORIA de producto
    @Query("SELECT l FROM Producto l INNER JOIN l.categoriaProducto c WHERE c.id = :filter")
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

    @Query(value = "SELECT SUM(IF((:fechaInicio IS NULL AND :fechaFin IS NULL) " +
            "OR (pedido.fecha_pedido BETWEEN :fechaInicio AND :fechaFin), " +
            "producto.precio_total - producto.costo_total, 0)) AS ganancia, producto.denominacion FROM producto LEFT JOIN pedido_has_producto " +
            "ON producto.id = pedido_has_producto.producto_id LEFT JOIN pedido ON pedido_has_producto.pedido_id = pedido.id GROUP BY producto.denominacion;"
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

}

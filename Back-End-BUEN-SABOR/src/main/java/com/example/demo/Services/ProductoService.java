package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.ProyeccionRankingProductos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ProductoService extends GenericService<Producto, Long> {

    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception;
    Page<Producto> filtroPaginado(Pageable pageable, String filtro) throws Exception;

    Page<Producto> filtroCategoriaPaginado(Pageable pageable, Long filter) throws Exception;

    List<Producto> filtroCategoria(Long filter) throws Exception;
    Page<ProyeccionRankingProductos> rankingProductosComida(Date fechaInicio, Date fechaFin, String campoDireccion, Pageable pageable) throws Exception;
    Page<ProyeccionRankingProductos> rankingProductosBebida(Date fechaInicio, Date fechaFin, String campoDireccion,  Pageable pageable) throws Exception;
}

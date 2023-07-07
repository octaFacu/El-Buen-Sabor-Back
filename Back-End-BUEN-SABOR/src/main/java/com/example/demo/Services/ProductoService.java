package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService extends GenericService<Producto, Long> {

    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception;
    Page<Producto> filtroPaginado(Pageable pageable, String filtro) throws Exception;

}

package com.example.demo.Repository;

import com.example.demo.Entidades.CategoriaProducto;
import com.example.demo.Entidades.Direccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProductoRepository extends GenericRepository<CategoriaProducto, Long>{

    @Query(value = "SELECT * FROM categoria_producto WHERE activo = true", nativeQuery = true)
    List<CategoriaProducto> findAllActivo();

}

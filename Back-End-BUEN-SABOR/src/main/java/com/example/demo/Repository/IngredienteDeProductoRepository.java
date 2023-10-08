package com.example.demo.Repository;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Repository
public interface IngredienteDeProductoRepository extends GenericRepository<IngredientesDeProductos,Long> {

    @Modifying
    @Transactional
    @Query(value = "CALL insertarIngredientesProducto(:productoId, :ingredienteId, :unidadMedidaId, :cantidad)", nativeQuery = true)
    void insertIngrediente(@Param("cantidad") Double cant,
                           @Param("unidadMedidaId") Long medidaId,
                           @Param("ingredienteId") Long ingredienteId,
                           @Param("productoId") Long productoId);

    @Query(value = "SELECT * FROM ingredientes_de_productos WHERE ingrediente_id = :idIngrediente")
    List<IngredientesDeProductos> findByIngredienteId(@Param("idIngrediente") Long idIngrediente);
}

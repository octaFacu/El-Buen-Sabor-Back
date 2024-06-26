package com.example.demo.Repository;

import com.example.demo.Entidades.Ingrediente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends GenericRepository<Ingrediente,Long> {

    @Query("SELECT i FROM Ingrediente i WHERE i.categoriaIngrediente.id = :idCategoriaIngrediente")
    List<Ingrediente> findByCategoriaIngrediente(@Param("idCategoria") Long idCategoria);

    @Query(value = "SELECT precio_compra FROM ingrediente WHERE id = :idIngrediente", nativeQuery = true)
    double findCosto(@Param("idIngrediente") Long idIngrediente);

    @Query(value = "SELECT precio_compra FROM ingrediente WHERE id = :idIngrediente", nativeQuery = true)
    double findIngredientesPorP(@Param("idIngrediente") Long idIngrediente);

}

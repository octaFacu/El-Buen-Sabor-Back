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

}

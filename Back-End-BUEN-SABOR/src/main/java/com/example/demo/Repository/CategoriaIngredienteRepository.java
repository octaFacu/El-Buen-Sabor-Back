package com.example.demo.Repository;

import com.example.demo.Entidades.CategoriaIngrediente;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaIngredienteRepository extends GenericRepository<CategoriaIngrediente, Long> {

    @Query(value = "SELECT c FROM CategoriaIngrediente c WHERE c.categoriaIngredientePadre.id = :idCategoriaPadre")
    List<CategoriaIngrediente> findByCategoriaIngredientePadreId(@Param("idCategoriaPadre") Long idCategoriaPadre);

    //Trae todas las categorias de ingrediente que NO tengan padre
    @Query(value = "SELECT c FROM CategoriaIngrediente c WHERE c.categoriaIngredientePadre IS NULL")
    List<CategoriaIngrediente> findPadres();

}

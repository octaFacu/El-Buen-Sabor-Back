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

    //Trae todas las categorias que no tienen padre y si tienen hijos
    @Query(value = "SELECT c FROM CategoriaIngrediente c WHERE c.categoriaIngredientePadre IS NULL AND EXISTS (\n" +
            "  SELECT 1\n" +
            "  FROM CategoriaIngrediente c1\n" +
            "  WHERE c1.categoriaIngredientePadre = c.id\n" +
            ")")
    List<CategoriaIngrediente> findPadresConHijos();

}

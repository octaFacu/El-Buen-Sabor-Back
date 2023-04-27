package com.example.demo.Repository;

import com.example.demo.Entidades.Categoria;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends GenericRepository<Categoria, Long> {

    @Query(value = "SELECT c FROM Categoria c WHERE c.categoriaPadre.id = :idCategoriaPadre")
    List<Categoria> findByCategoriaPadreId(@Param("idCategoriaPadre") Long idCategoriaPadre);

    //Trae todas las categorias de ingrediente que NO tengan padre
    @Query(value = "SELECT c FROM Categoria c WHERE c.categoriaPadre IS NULL")
    List<Categoria> findPadres();

}

package com.example.demo.Repository;

import com.example.demo.Entidades.UnidadDeMedida;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmRepository extends GenericRepository<UnidadDeMedida,Long> {

    @Query("SELECT u FROM UnidadDeMedida u WHERE u.padre.id = :idPadre")
    List<UnidadDeMedida> findByPadreId(@Param("idPadre") Long idPadre);

}

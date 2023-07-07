package com.example.demo.Repository;

import com.example.demo.Entidades.Direccion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends GenericRepository<Direccion,Long> {

    @Query(value = "SELECT * FROM direccion WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Direccion>findByUsuarioId(@Param("idUsuario") String idUsaurio);

}

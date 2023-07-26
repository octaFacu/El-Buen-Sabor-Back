package com.example.demo.Repository;

import com.example.demo.Entidades.Direccion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionRepository extends GenericRepository<Direccion,Long> {

    @Query(value = "SELECT * FROM direccion WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Direccion>findByUsuarioId(@Param("idUsuario") String idUsaurio);

    @Query(value = "SELECT * FROM direccion a WHERE a.calle = :#{#direccion.calle} AND a.nro_casa = :#{#direccion.nroCasa} " +
            "AND a.piso_dpto = :#{#direccion.pisoDpto} AND a.id_usuario = :usuarioId", nativeQuery = true)
    Optional<Direccion> findByDireccionAndUsuarioId(@Param("direccion") Direccion direccion,
                                                  @Param("usuarioId") String usuarioId);

    @Query(value = "SELECT IF(COUNT(*) > 0, TRUE, FALSE) " +
            "FROM direccion a WHERE a.calle = :#{#direccion.calle} AND a.nro_casa = :#{#direccion.nroCasa} " +
            "AND a.piso_dpto = :#{#direccion.pisoDpto} AND a.activo = TRUE AND a.id_usuario = :usuarioId", nativeQuery = true)
    byte segundaVerificacionDireccion(@Param("direccion") Direccion direccion,
                                         @Param("usuarioId") String usuarioId);
}

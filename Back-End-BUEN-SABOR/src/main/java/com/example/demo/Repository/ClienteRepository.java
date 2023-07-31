package com.example.demo.Repository;

import com.example.demo.Entidades.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends GenericRepository<Cliente,Long> {
    @Query(value = "SELECT id_cliente FROM cliente WHERE usuario_id LIKE :id_usuario",nativeQuery = true)
    Long findbyId_usuario(@Param("id_usuario") String id_usuario);
}

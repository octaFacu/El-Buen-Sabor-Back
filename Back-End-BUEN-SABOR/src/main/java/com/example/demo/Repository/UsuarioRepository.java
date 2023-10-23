package com.example.demo.Repository;

import com.example.demo.Entidades.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, String> {

    @Query(value ="SELECT * FROM usuario where email LIKE \"%@BuenSabor.com.ar\"",nativeQuery = true)
    List<Usuario> traerEmpleados();

}

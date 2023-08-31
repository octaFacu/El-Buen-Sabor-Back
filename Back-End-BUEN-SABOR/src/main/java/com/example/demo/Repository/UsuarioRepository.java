package com.example.demo.Repository;

import com.example.demo.Entidades.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, String> {
}

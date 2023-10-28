package com.example.demo.Services;

import com.example.demo.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UsuarioService extends GenericService<Usuario, String> {

    public Page<Usuario> traerEmpleados(Pageable pageable) throws Exception;

}

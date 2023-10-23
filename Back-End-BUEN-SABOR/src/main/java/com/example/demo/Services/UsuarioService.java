package com.example.demo.Services;

import com.example.demo.Entidades.Usuario;

import java.util.List;

public interface UsuarioService extends GenericService<Usuario, String> {

    public List<Usuario> traerEmpleados() throws Exception;

}

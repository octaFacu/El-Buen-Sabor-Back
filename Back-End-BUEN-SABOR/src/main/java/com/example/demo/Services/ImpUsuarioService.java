package com.example.demo.Services;

import com.example.demo.Entidades.Usuario;
import com.example.demo.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImpUsuarioService extends GenericServiceImpl<Usuario, String>  implements UsuarioService {
    @Autowired
    UsuarioRepository repository;
    @Override
    public Page<Usuario> traerEmpleados(Pageable pageable) throws Exception {
        try {
            Page<Usuario> empleados = repository.traerEmpleados(pageable);
            return empleados;
        }
        catch (Exception e){
            throw new Exception("Error Al obtener los empleados");
        }
    }

    public Usuario actualizaRol(String idUsuario,String nombreRol){
        Usuario usuario = repository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuario.setNombreRol(nombreRol);
        return repository.save(usuario);
    }

}

package com.example.demo.Services;

import com.example.demo.Entidades.Usuario;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImpUsuarioService extends GenericServiceImpl<Usuario, String>  implements UsuarioService {
    @Autowired
    UsuarioRepository repository;
    @Override
    public List<Usuario> traerEmpleados() throws Exception {
        try {
            List<Usuario> empleados = repository.traerEmpleados();
            return empleados;
        }
        catch (Exception e){
            throw new Exception("Error Al obtener los empleados");
        }
    }
}

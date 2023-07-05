package com.example.demo.Services;

import com.example.demo.Entidades.Usuario;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ImpUsuarioService extends GenericServiceImpl<Usuario, String>  implements UsuarioService {
}

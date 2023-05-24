package com.example.demo.Controller;

import com.example.demo.Entidades.Usuario;
import com.example.demo.Services.ImpUsuarioService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController extends GenericControllerImpl<Usuario, ImpUsuarioService> {
}

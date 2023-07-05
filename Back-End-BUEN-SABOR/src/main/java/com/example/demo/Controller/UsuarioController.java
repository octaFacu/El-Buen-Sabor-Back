package com.example.demo.Controller;

import com.example.demo.Entidades.Usuario;
import com.example.demo.Services.ImpUsuarioService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usuario")
public class UsuarioController extends GenericControllerImpl<Usuario,String, ImpUsuarioService> {

}

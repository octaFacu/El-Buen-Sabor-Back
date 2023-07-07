package com.example.demo.Controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Services.ImpClienteService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cliente")
public class ClienteController extends GenericControllerImpl<Cliente,Long, ImpClienteService> {
}

package com.example.demo.Controller;


import com.example.demo.Entidades.Producto;
import com.example.demo.Services.ImpProductoService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends GenericControllerImpl<Producto, ImpProductoService> {
}

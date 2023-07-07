package com.example.demo.Controller;

import com.example.demo.Entidades.CategoriaProducto;
import com.example.demo.Services.ImpCategoriaProductoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/categoriaProducto")
public class CategoriaProductoController extends GenericControllerImpl<CategoriaProducto,Long, ImpCategoriaProductoService> {
}

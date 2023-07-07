package com.example.demo.Controller;

import com.example.demo.Entidades.Direccion;
import com.example.demo.Services.ImpDireccionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/direccion")
public class DireccionController extends GenericControllerImpl<Direccion,Long, ImpDireccionService> {
}

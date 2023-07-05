package com.example.demo.Controller;

import com.example.demo.Entidades.Factura;
import com.example.demo.Services.ImpFacturaService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/factura")
public class FacturaController extends GenericControllerImpl<Factura,Long, ImpFacturaService> {
}

package com.example.demo.Controller;

import com.example.demo.Entidades.MetodoDePago;
import com.example.demo.Services.ImpMetodoDePagoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/metodoDePago")
public class MetodoDePagoController extends GenericControllerImpl<MetodoDePago,Long, ImpMetodoDePagoService> {
}

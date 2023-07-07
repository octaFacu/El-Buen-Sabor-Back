package com.example.demo.Controller;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Services.ImpPedidoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pedido")
public class PedidoController extends GenericControllerImpl<Pedido,Long, ImpPedidoService> {
}

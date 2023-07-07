package com.example.demo.Controller;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Services.ImpFavoritoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/favorito")
public class FavoritoController  extends GenericControllerImpl<Favorito,Long, ImpFavoritoService> {
}

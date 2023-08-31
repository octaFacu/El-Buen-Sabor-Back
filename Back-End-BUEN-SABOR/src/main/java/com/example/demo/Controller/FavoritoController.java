package com.example.demo.Controller;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Services.ImpFavoritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/favorito")
public class FavoritoController  extends GenericControllerImpl<Favorito,Long, ImpFavoritoService> {

    @GetMapping("/buscar/{id_cliente}")
    public ResponseEntity<List<ProyeccionProductoFavorito>> buscarFavoritoPorUsuario(@PathVariable long id_cliente) throws Exception {
        try {
            List<ProyeccionProductoFavorito> proyeccion = service.findbyId_cliente(id_cliente);
            return ResponseEntity.ok(proyeccion);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}

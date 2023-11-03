package com.example.demo.Controller;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Services.ImpFavoritoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/favorito")
public class FavoritoController  extends GenericControllerImpl<Favorito,Long, ImpFavoritoService> {

    @GetMapping("/buscar/{id_cliente}")
    public ResponseEntity<Page<ProyeccionProductoFavorito>> buscarFavoritoPorUsuario(@PathVariable long id_cliente, @RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "5") Integer size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionProductoFavorito> proyeccion = service.findbyId_cliente(id_cliente, pageable);
            return ResponseEntity.ok(proyeccion);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}

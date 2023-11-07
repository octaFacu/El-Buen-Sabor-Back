package com.example.demo.Controller;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Services.ImpFavoritoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;

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


    @GetMapping("/getAllUsuarioId/{id_usuario}")
    public ResponseEntity<List<Favorito>> getAllByUsuarioId(@PathVariable String id_usuario) throws Exception {
        try {
            List<Favorito> favoritos = service.getAllByUsuarioId(id_usuario);
            return ResponseEntity.ok(favoritos);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/saveFavorito/{id_usuario}")
    public ResponseEntity<Favorito> saveFavorito(@PathVariable String id_usuario, @RequestBody Producto producto) throws Exception {
        try {
            Favorito favorito = service.saveFavorito(id_usuario, producto);
            return ResponseEntity.ok(favorito);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/deleteFavorito/{id_usuario}/{id_producto}")
    public ResponseEntity<?> deleteFavorito(@PathVariable String id_usuario, @PathVariable long id_producto) throws Exception {
        try {
            service.deleteFavorito(id_usuario, id_producto);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminado");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}

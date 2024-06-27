package com.example.demo.Controller;

import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProducto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Security.PublicEndpoint;
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

    @PublicEndpoint
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


    @PublicEndpoint
    @GetMapping("/getAllUsuarioId/{id_usuario}")
    public ResponseEntity<List<Favorito>> getAllByUsuarioId(@PathVariable String id_usuario) throws Exception {
        try {
            List<Favorito> favoritos = service.getAllByUsuarioId(id_usuario);
            return ResponseEntity.ok(favoritos);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
  /*
    @PublicEndpoint
    @GetMapping("/getProductoFavorito/{id}")
    public ResponseEntity<Object[]> getProductoFavorito(@PathVariable long id) throws Exception {
        try {
            Object[] producto = service.traerProductoFavorito(id);
            Producto prod = new Producto();
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } */

    @PublicEndpoint
    @GetMapping("/getProductoFavorito/{id}")
    public ResponseEntity<ProyeccionProducto> getProductoFavorito(@PathVariable long id) throws Exception {
        try {
            ProyeccionProducto producto = service.traerProductoFavorito(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PublicEndpoint
    @PostMapping("/saveFavorito/{id_usuario}")
    public ResponseEntity<Favorito> saveFavorito(@PathVariable String id_usuario, @RequestBody Producto producto) throws Exception {
        try {
            Favorito favorito = service.saveFavorito(id_usuario, producto);
            return ResponseEntity.ok(favorito);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
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

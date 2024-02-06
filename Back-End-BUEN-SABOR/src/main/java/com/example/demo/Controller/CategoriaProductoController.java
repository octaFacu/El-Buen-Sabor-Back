package com.example.demo.Controller;

import com.example.demo.Entidades.CategoriaProducto;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpCategoriaProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/categoriaProducto")
public class CategoriaProductoController extends GenericControllerImpl<CategoriaProducto,Long, ImpCategoriaProductoService> {

    //Traigo todas las categorias de los productos que esten activas
    @PublicEndpoint
    @GetMapping("/getAllActivo")
    public ResponseEntity<?> getAllActivo() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllActivo());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}

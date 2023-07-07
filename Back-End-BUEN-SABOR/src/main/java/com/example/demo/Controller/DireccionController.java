package com.example.demo.Controller;

import com.example.demo.Entidades.Direccion;
import com.example.demo.Services.DireccionService;
import com.example.demo.Services.ImpDireccionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/direccion")
public class DireccionController extends GenericControllerImpl<Direccion,Long, ImpDireccionService> {

    @Autowired
    private DireccionService direccionService;
    @GetMapping("/porUsuario")
    public ResponseEntity<List<Direccion>> findDireccionesByUsuarioId(@RequestParam("idUsuario") String idUsuario){
        List<Direccion> direcciones = direccionService.findByUsuarioId(idUsuario);
        return ResponseEntity.ok(direcciones);
    }
}

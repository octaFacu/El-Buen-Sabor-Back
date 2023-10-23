package com.example.demo.Controller;

import com.example.demo.Entidades.Usuario;
import com.example.demo.Services.ImpUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usuario")
public class UsuarioController extends GenericControllerImpl<Usuario,String, ImpUsuarioService> {

    @GetMapping("/traerEmpleados")
    public ResponseEntity<?> traerEmpleados(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.traerEmpleados());
        }
        catch (Exception e){
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("status", 500);
            respuestaDelError.put("msj", "Error al Obtener los Empleados");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }

}

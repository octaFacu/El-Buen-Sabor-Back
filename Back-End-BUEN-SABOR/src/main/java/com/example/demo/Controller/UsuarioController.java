package com.example.demo.Controller;

import com.example.demo.Config.ApiAuth0.Auth0Api;
import com.example.demo.Entidades.Usuario;
import com.example.demo.Security.AdminOnly;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpUsuarioService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usuario")
public class UsuarioController extends GenericControllerImpl<Usuario,String, ImpUsuarioService> {

    @PublicEndpoint
    @GetMapping("/traerEmpleados")
    public ResponseEntity<?> traerEmpleados(  @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "5") Integer size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(service.traerEmpleados(pageable));
        }
        catch (Exception e){
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("status", 500);
            respuestaDelError.put("msj", "Error al Obtener los Empleados");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }
    Auth0Api api = new Auth0Api();

    @PublicEndpoint
    @PostMapping("/AgregarRol/{idAuth0}")
    public ResponseEntity<?> prueba(@PathVariable String idAuth0,  @RequestBody Map<String, String> requestBody){
        try {

            String idRol = requestBody.get("idRol");
            String nombreRol = requestBody.get("nombreRol");

            service.actualizaRol(idAuth0, nombreRol);

            String scope = idRol; // No es necesario convertirlo de nuevo
            String respuesta = api.agregarRol(idAuth0, scope,3);


            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PublicEndpoint
    @GetMapping("/ObtenerToken")
    public ResponseEntity<?> ObtenerToken(){
        api.Token();
        return ResponseEntity.status(HttpStatus.OK).body("token");
    }

    @PublicEndpoint
    @PostMapping("/BorrarRol/{idAuth0}")
    public ResponseEntity<?> borrarRol(@PathVariable String idAuth0,@RequestBody Map<String, String> requestBody){
        try {
            String idRol = requestBody.get("idRol");
            String nombreRol = requestBody.get("nombreRol");

            service.actualizaRol(idAuth0, nombreRol);

            String scope = idRol;
          String respuesta =  api.deleteRol(idAuth0, scope, 3);

            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("E");
        }
    }

}

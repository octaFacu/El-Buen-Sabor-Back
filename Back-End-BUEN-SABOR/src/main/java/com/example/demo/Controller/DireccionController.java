package com.example.demo.Controller;

import com.example.demo.Entidades.Direccion;
import com.example.demo.Entidades.Excepciones.DireccionUsuarioExisteException;
import com.example.demo.Services.DireccionService;
import com.example.demo.Services.ImpDireccionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/direccion")
public class DireccionController extends GenericControllerImpl<Direccion,Long, ImpDireccionService> {

    @Autowired
    private DireccionService direccionService;
    @GetMapping("/porUsuario")
    public ResponseEntity<?> findDireccionesByUsuarioId(@RequestParam("idUsuario") String idUsuario) {
        try {
            List<Direccion> direcciones = direccionService.findByUsuarioId(idUsuario);
            return ResponseEntity.ok(direcciones);
        } catch (Exception e) {
            // Manejar excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar la solicitud.");
        }
    }

    @PutMapping("/actualizar/direccion/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Direccion entity,@RequestParam("idUsuario") String idUsuario) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity,idUsuario));
        } catch (DireccionUsuarioExisteException e) {
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 400);
            respuestaDelError.put("msj", "La dirección ya existe y se encuentra activa.");
            return ResponseEntity.badRequest().body(respuestaDelError);
        }catch (EntityNotFoundException e) {
        // Si no encontramos al usuario. nos devuelve un no encontrado
        return ResponseEntity.notFound().build();
        } catch (Exception e) {
        // Para los demas errores nos devuelve un mensaje generico
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 500);
            respuestaDelError.put("msj", "Error al verificar y crear dirección");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }

    @PatchMapping("/verificar-crear-direccion/{usuarioId}")
    public ResponseEntity<?> verificarYCrearDireccion(@PathVariable String usuarioId, @RequestBody Direccion direccion) {
        try {
            // Verificar y crear la dirección utilizando el ID del usuario
            service.verificadorDirUsuario(usuarioId, direccion);
            return ResponseEntity.ok("Verificación y creación de dirección exitosa");
        } catch (DireccionUsuarioExisteException e) {
            // Si la dirección ya existe y está activa, devuelve una respuesta 400 con el mensaje de error
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 400);
            respuestaDelError.put("msj", "La dirección ya existe y se encuentra activa.");
            return ResponseEntity.badRequest().body(respuestaDelError);
        } catch (Exception e) {
            // Para los demas errores nos devuelve un mensaje generico
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 500);
            respuestaDelError.put("msj", "Error al verificar y crear dirección");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }
}

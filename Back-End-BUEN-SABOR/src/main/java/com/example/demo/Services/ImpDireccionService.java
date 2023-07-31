package com.example.demo.Services;

import com.example.demo.Entidades.Direccion;

import com.example.demo.Entidades.Excepciones.DireccionUsuarioExisteException;
import com.example.demo.Entidades.Usuario;
import com.example.demo.Repository.DireccionRepository;
import com.example.demo.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ImpDireccionService extends GenericServiceImpl<Direccion,Long> implements DireccionService {

    @Autowired
    protected DireccionRepository repository;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Override
    public List<Direccion> findByUsuarioId(String usuarioId) {
        List<Direccion> direcciones = repository.findByUsuarioId(usuarioId);
        return direcciones;
    }

    @Override
    @Transactional
    public Direccion update(Long id, Direccion direccion, String usuarioId) throws Exception {
        // obtenemos la entidad
    try {
        Optional<Direccion> entidad = repository.findById(id);
        if(!entidad.isPresent()){
            throw new NoSuchElementException("No se ah encontrado una direccion con ese id");
        }
        Direccion entidadActualizar = entidad.get();
        // comprobamos si la entidad ya existe en ese usuario y esta activa.
        byte direccionExisteActiva = repository.segundaVerificacionDireccion(direccion, usuarioId);
        if(direccionExisteActiva == 1){
            // si es verdadera la comprobacion lanzamos la excepcion
            throw new DireccionUsuarioExisteException("La dirección ya existe");
        }else {
            // si no actualizamos nuestra direccion
            entidadActualizar = repository.save(direccion);
            return entidadActualizar;
        }
    } catch (NoSuchElementException e) {
        throw e;
    } catch (DireccionUsuarioExisteException e){
        throw e;
    } catch (Exception e) {
        throw new Exception("Error al verificar y crear dirección", e);
    }
    }

    @Override
    @Transactional
    public void verificadorDirUsuario(String usuarioId, Direccion direccion) {
        // Lo primero que hacemos es buscar si existe una dirección idéntica
        Optional<Direccion> direccionExistente = repository.findByDireccionAndUsuarioId(direccion, usuarioId);

        if (direccionExistente.isPresent()) {
            // Si la dirección existe, obtenemos el resultado de la segunda verificación
            byte direccionExisteActiva = repository.segundaVerificacionDireccion(direccion, usuarioId);
            System.out.println(direccionExisteActiva);

            if (direccionExisteActiva == 1) {
                // Si la dirección existe y está activa, mostramos un mensaje de advertencia
                throw new DireccionUsuarioExisteException("La dirección ya existe y está activa.");
            } else {
                // Si la dirección existe pero no está activa, reactivamos la dirección (establecer activo en true)
                Direccion direccionGuardada = direccionExistente.get();
                repository.softDeleteById((long) direccionGuardada.getIdDireccion());
            }
        } else {
            // Si la dirección no existe, creamos una nueva dirección y la asociamos al usuario
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            direccion.setUsuario(usuario);
            direccion.setActivo(true);
            repository.save(direccion);
        }
    }

}

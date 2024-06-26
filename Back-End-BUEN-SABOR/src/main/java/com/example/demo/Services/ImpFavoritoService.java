package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProducto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Repository.ClienteRepository;
import com.example.demo.Repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ImpFavoritoService extends GenericServiceImpl<Favorito,Long> implements FavoritoService{

    @Autowired
    FavoritoRepository repository;
    @Autowired
    ClienteRepository clienteRepository;
    @Override
    public Page<ProyeccionProductoFavorito> findbyId_cliente(long id_cliente, Pageable pageable) throws Exception {
        try{

            return repository.findbyId_cliente(id_cliente, pageable);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Favorito> getAllByUsuarioId(String id_usuario) throws Exception {
        try{

            Cliente cliente = clienteRepository.findClienteXUsuarioId(id_usuario);

            return repository.findAllByClienteId(cliente.getIdCliente());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Favorito saveFavorito (String id_usuario, Producto producto) throws Exception {
        try{

            Cliente cliente = clienteRepository.findClienteXUsuarioId(id_usuario);
            Favorito nuevoFav = new Favorito(0l, cliente, producto, true);

            return repository.save(nuevoFav);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void deleteFavorito(String id_usuario, long id_producto) throws Exception {
        try{
            Favorito favorito = repository.findFavoritoByClienteAndProductoId(id_usuario, id_producto);
            repository.delete(favorito);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
/*
    public Object[] traerProductoFavorito(long id) throws Exception {
        try {
            Object[] result = repository.traerProducto(id);
            System.out.println(Arrays.toString(result)); // Imprimir el resultado para depuración
            return result;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    } */

    public ProyeccionProducto traerProductoFavorito(long id) throws Exception {
        try {
            return repository.traerProducto(id);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

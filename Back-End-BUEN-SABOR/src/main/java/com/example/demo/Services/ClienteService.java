package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;

import java.util.List;

public interface ClienteService extends GenericService<Cliente,Long> {
    Long findbyId_cliente(String id_usuario) throws Exception;
}

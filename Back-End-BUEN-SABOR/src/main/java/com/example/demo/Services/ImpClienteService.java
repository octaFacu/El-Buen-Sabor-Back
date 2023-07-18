package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpClienteService extends GenericServiceImpl<Cliente, Long> implements ClienteService {

    @Autowired
    ClienteRepository repositorio;
   @Override
    public Long findbyId_cliente(String id_usuario) throws Exception {
        try{
            return repositorio.findbyId_usuario(id_usuario);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

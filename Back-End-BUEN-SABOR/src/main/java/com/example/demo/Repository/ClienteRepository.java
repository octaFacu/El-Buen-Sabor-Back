package com.example.demo.Repository;

import com.example.demo.Entidades.Cliente;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends GenericRepository<Cliente,Long> {
}

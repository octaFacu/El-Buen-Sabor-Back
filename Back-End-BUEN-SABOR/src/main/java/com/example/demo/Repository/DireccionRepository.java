package com.example.demo.Repository;

import com.example.demo.Entidades.Direccion;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends GenericRepository<Direccion,Long> {
}

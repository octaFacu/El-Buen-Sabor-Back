package com.example.demo.Repository;

import com.example.demo.Entidades.MetodoDePago;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoDePagoRepository extends GenericRepository<MetodoDePago, Long> {
}

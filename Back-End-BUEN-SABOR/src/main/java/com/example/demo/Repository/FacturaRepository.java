package com.example.demo.Repository;

import com.example.demo.Entidades.Factura;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends GenericRepository<Factura, Long> {
}

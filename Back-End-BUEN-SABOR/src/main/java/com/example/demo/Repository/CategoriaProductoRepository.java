package com.example.demo.Repository;

import com.example.demo.Entidades.CategoriaProducto;
import com.example.genericos.genericos.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProductoRepository extends GenericRepository<CategoriaProducto, Long>{
}

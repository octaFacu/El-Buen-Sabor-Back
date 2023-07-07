package com.example.demo.Repository;

import com.example.demo.Entidades.Favorito;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends GenericRepository<Favorito, Long> {
}


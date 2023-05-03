package com.example.demo.Services;

import com.example.demo.Entidades.Favorito;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImpFavoritoService extends GenericServiceImpl<Favorito,Long> implements FavoritoService{
}

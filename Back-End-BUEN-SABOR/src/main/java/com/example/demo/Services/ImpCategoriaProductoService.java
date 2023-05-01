package com.example.demo.Services;

import com.example.demo.Entidades.CategoriaProducto;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImpCategoriaProductoService extends GenericServiceImpl<CategoriaProducto,Long> implements CategoriaProductoService{
}

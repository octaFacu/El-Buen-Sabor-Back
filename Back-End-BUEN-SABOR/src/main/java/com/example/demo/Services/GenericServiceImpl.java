package com.example.demo.Services;

import com.example.demo.Repository.GenericRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

    @Autowired
    GenericRepository<T,ID> repository;

    @Override
    public T findById(ID id) throws Exception {
        try {
            Optional<T> entity = repository.findById(id);
            return entity.get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<T> findAll() throws Exception {
        try {
            List<T> entitys = repository.findAll();
            return entitys;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T save(T entity) throws Exception {
        try {
            entity = repository.save(entity);
            return entity;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T update(ID id, T entity) throws Exception {
        try {
            Optional<T> entidad = repository.findById(id);
            T entidadActualizar = entidad.get();
            entidadActualizar = repository.save(entity);
            return entidadActualizar;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteById(ID id) throws Exception {
        try {
            repository.deleteById(id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Page<T> findAllPaged(Pageable pageable) throws Exception {

        try {

            Page<T> entities = repository.findAll(pageable);
            return entities;

        }catch(Exception e) {

            throw new Exception(e.getMessage());
        }
    }

}

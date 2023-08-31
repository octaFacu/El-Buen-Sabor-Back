package com.example.demo.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, ID extends Serializable> {

    T findById(ID id) throws Exception;
    List<T> findAll() throws Exception;
    T save(T entity) throws Exception;
    T update(ID id, T entity) throws Exception;
    void deleteById(ID id) throws Exception;
    void softDeleteById(ID id) throws Exception;
    Page<T> findAllPaged(Pageable pageable) throws Exception;

}

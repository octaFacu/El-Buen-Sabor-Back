package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

public interface GenericController<T, ID extends Serializable> {

    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getOne(@PathVariable ID id);
    public ResponseEntity<?> save(@RequestBody T entity);
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity);
    public ResponseEntity<?> delete(@PathVariable ID id);
    public ResponseEntity<?> softDelete(@PathVariable ID id);
    public ResponseEntity<?> getAllPaged(@RequestParam Integer page, @RequestParam Integer size);

}

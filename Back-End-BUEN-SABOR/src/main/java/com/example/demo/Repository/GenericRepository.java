package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.activo = CASE WHEN e.activo = true THEN false ELSE true END WHERE e.id = :id")
    void softDeleteById(@Param("id") ID id);
}

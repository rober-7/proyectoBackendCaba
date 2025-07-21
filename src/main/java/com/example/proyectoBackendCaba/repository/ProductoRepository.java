package com.example.proyectoBackendCaba.repository;

import com.example.proyectoBackendCaba.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Búsqueda por nombre (contiene)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Búsqueda por categoría
    List<Producto> findByCategoriaIgnoreCase(String categoria);

    // Búsqueda por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max")
    List<Producto> findByPrecioBetween(@Param("min") Double min, @Param("max") Double max);

    // Búsqueda por stock disponible
    List<Producto> findByStockGreaterThan(Integer cantidad);

}
package com.example.proyectoBackendCaba.service;

import com.example.proyectoBackendCaba.model.Producto;
import com.example.proyectoBackendCaba.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // CRUD básico
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto crear(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        validarProducto(producto);
        Producto existente = obtenerPorId(id);
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setCategoria(producto.getCategoria());
        existente.setImagenUrl(producto.getImagenUrl());
        existente.setStock(producto.getStock());
        return productoRepository.save(existente);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // Métodos de búsqueda
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public List<Producto> buscarPorRangoPrecio(Double min, Double max) {
        return productoRepository.findByPrecioBetween(min, max);
    }

    public List<Producto> buscarConStockDisponible() {
        return productoRepository.findByStockGreaterThan(0);
    }

    // Validación
    private void validarProducto(Producto producto) {
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}

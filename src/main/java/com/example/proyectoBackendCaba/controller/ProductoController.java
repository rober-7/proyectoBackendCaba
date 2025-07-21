package com.example.proyectoBackendCaba.controller;

import com.example.proyectoBackendCaba.model.Producto;
import com.example.proyectoBackendCaba.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.crear(producto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints de búsqueda
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {

        if (nombre != null) {
            return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
        }
        if (categoria != null) {
            return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
        }
        if (precioMin != null && precioMax != null) {
            return ResponseEntity.ok(productoService.buscarPorRangoPrecio(precioMin, precioMax));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> buscarDisponibles() {
        return ResponseEntity.ok(productoService.buscarConStockDisponible());
    }
}
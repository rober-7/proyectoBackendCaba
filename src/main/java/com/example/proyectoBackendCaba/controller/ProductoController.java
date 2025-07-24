package com.example.proyectoBackendCaba.controller;

import com.example.proyectoBackendCaba.model.Producto;
import com.example.proyectoBackendCaba.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @Operation(summary = "Redireccionar al index", description = "Redirige a la página principal del frontend (index.html)")
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista con todos los productos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }



    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }


    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.crear(producto), HttpStatus.CREATED);
    }


    @Operation(summary = "Actualizar un producto", description = "Modifica los datos de un producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar productos por nombre, categoría o rango de precio", description = "Permite buscar productos por nombre, categoría o por un rango de precios")
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(
            @Parameter(description = "Nombre del producto") @RequestParam(required = false) String nombre,
            @Parameter(description = "Categoría del producto") @RequestParam(required = false) String categoria,
            @Parameter(description = "Precio mínimo") @RequestParam(required = false) Double precioMin,
            @Parameter(description = "Precio máximo") @RequestParam(required = false) Double precioMax) {

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


    @Operation(summary = "Listar productos disponibles", description = "Devuelve todos los productos que tienen stock disponible")
    @ApiResponse(responseCode = "200", description = "Productos disponibles listados correctamente")
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> buscarDisponibles() {
        return ResponseEntity.ok(productoService.buscarConStockDisponible());
    }
}
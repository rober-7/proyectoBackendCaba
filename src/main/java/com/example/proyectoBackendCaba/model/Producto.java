package com.example.proyectoBackendCaba.model;

import jakarta.persistence.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@Table(name = "productos")
@Schema(description = "Entidad que representa un producto del catálogo")
public class Producto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Zapatillas deportivas")
    private String nombre;

    @Column(length = 1000)
    @Schema(description = "Descripción detallada del producto", example = "Zapatillas livianas para running con suela de goma antideslizante")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Precio del producto", example = "12999.50")
    private Double precio;

    @Schema(description = "Categoría a la que pertenece el producto", example = "Calzado")
    private String categoria;

    @Column(name = "imagen_url")
    @Schema(description = "URL de la imagen del producto", example = "https://miapp.com/img/zapatillas123.jpg")
    private String imagenUrl;

    @Column(nullable = false)
    @Schema(description = "Cantidad de unidades disponibles en stock", example = "25")
    private Integer stock;
}

package com.example.proyectoBackendCaba.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    private String categoria;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(nullable = false)
    private Integer stock;
}

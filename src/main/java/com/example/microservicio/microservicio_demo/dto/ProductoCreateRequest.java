package com.example.microservicio.microservicio_demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductoCreateRequest(
        String nombre,
        String descripcion,
        String imagen,
        BigDecimal precio,
        Integer stock,
        Integer descuento,
        Integer valoracion,
        LocalDate fechaIngreso,
        Integer estado,
        Integer idMarca,
        Integer idCategoria
) {}

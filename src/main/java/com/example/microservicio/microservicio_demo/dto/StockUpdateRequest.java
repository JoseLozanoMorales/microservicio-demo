package com.example.microservicio.microservicio_demo.dto;

public record StockUpdateRequest(
        Integer idProducto,
        Integer cantidad,
        Double precioVenta
) {}
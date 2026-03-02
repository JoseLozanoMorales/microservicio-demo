package com.example.microservicio.microservicio_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/b/productos")
public class BController {

    private final ProductosClient productosClient;

    public BController(ProductosClient productosClient) {
        this.productosClient = productosClient;
    }

    @GetMapping
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(productosClient.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(productosClient.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Object body) {
        // A devuelve vacío en tu controller, pero igual devolvemos algo
        String r = productosClient.crear(body);
        return ResponseEntity.ok(r == null ? "OK" : r);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable Integer id, @RequestBody Object body) {
        String r = productosClient.editar(id, body);
        return ResponseEntity.ok(r == null ? "OK" : r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrar(@PathVariable Integer id) {
        String r = productosClient.borrar(id);
        return ResponseEntity.ok(r == null ? "OK" : r);
    }
}
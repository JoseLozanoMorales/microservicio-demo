package com.example.microservicio.microservicio_demo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/b")
public class BController {

    private final ProductosClient productosClient;

    public BController(ProductosClient productosClient) {
        this.productosClient = productosClient;
    }

    @GetMapping("/productos")
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(productosClient.getProductos());
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<String> editar(@PathVariable Integer id, @RequestBody Object body) {
        return ResponseEntity.ok(productosClient.editarProducto(id, body));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<String> borrar(@PathVariable Integer id) {
        return ResponseEntity.ok(productosClient.borrarProducto(id));
    }
}
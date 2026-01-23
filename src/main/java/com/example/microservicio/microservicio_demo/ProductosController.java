package com.example.microservicio.microservicio_demo;

import com.example.microservicio.microservicio_demo.dto.ProductoCreateRequest;
import com.example.microservicio.microservicio_demo.dto.ProductoImagenCreateRequest;
import com.example.microservicio.microservicio_demo.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.Statement;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    private final JdbcTemplate jdbc;
    private final ProductoService service;

    public ProductosController(JdbcTemplate jdbc, ProductoService service) {
        this.jdbc = jdbc;
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        // Consulta actualizada para soportar la nueva tabla producto_imagenes
        // Obtiene la imagen marcada como portada (portada = true)
        String sql = """
            SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.stock, 
                   p.descuento, p.valoracion, p.fecha_ingreso, p.estado, 
                   m.nombre as marca, c.nombre as categoria,
                   (SELECT '/api/productos/imagenes/render/' || CAST(pi.id_imagen AS VARCHAR) 
                    FROM producto_imagenes pi 
                    WHERE pi.id_producto = p.id_producto AND pi.portada = true LIMIT 1) as imagen
            FROM productos p
            LEFT JOIN marca m ON p.id_marca = m.id_marca
            LEFT JOIN categoria c ON p.id_categoria = c.id_categoria
            ORDER BY p.id_producto DESC
        """;
        return jdbc.queryForList(sql);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoCreateRequest req) {
        service.insertar(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/imagenes")
    public List<Map<String, Object>> listarImagenes(@PathVariable Integer id) {
        // Seleccionamos ID y metadatos (sin el BLOB pesado)
        String sql = "SELECT id_imagen, id_producto, portada, galeria FROM producto_imagenes WHERE id_producto = ?";
        List<Map<String, Object>> imagenes = jdbc.queryForList(sql, id);
        
        // Construimos la URL de renderizado para que el frontend pueda mostrarla
        imagenes.forEach(img -> {
            img.put("url", "/api/productos/imagenes/render/" + img.get("id_imagen"));
        });
        return imagenes;
    }

    @DeleteMapping("/imagenes/{idImagen}")
    public ResponseEntity<?> eliminarImagen(@PathVariable Integer idImagen) {
        // Usamos el procedimiento almacenado para eliminar
        jdbc.update("CALL sp_eliminar_imagen(?)", idImagen);
        return ResponseEntity.ok().build();
    }

    // Nuevo endpoint para subir imagen directamente a la BD (BLOB)
    @PostMapping("/{id}/imagenes/upload")
    public ResponseEntity<?> uploadImagenProducto(@PathVariable Integer id,
                                                  @RequestParam("file") MultipartFile file,
                                                  @RequestParam(value = "portada", defaultValue = "false") boolean portada,
                                                  @RequestParam(value = "galeria", defaultValue = "true") boolean galeria) {
        try {
            byte[] bytes = file.getBytes();
            // Insertamos directamente en la columna 'imagen' (BYTEA)
            String sql = "CALL sp_subir_imagen(?, ?, ?, ?)";
            
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setBytes(2, bytes);
                ps.setBoolean(3, portada);
                ps.setBoolean(4, galeria);
                return ps;
            });
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al procesar imagen: " + e.getMessage());
        }
    }

    // Nuevo endpoint para servir la imagen desde la BD
    @GetMapping("/imagenes/render/{idImagen}")
    public ResponseEntity<byte[]> renderImagen(@PathVariable Integer idImagen) {
        try {
            String sql = "SELECT imagen FROM producto_imagenes WHERE id_imagen = ?";
            byte[] imagen = jdbc.queryForObject(sql, byte[].class, idImagen);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG) // O detecta el tipo si es necesario
                    .body(imagen);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.ProductoCreateRequest;
import com.example.microservicio.microservicio_demo.dto.ProductoImagenCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductoService {

    private final JdbcTemplate jdbc;

    public ProductoService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void insertar(ProductoCreateRequest r) {

    String sql = "CALL public.insertar_producto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    jdbc.update(sql,
            r.nombre(),
            r.descripcion(),
            r.precio(),
            r.stock(),
            r.descuento(),
            r.valoracion(),
            r.fechaIngreso() != null ? java.sql.Date.valueOf(r.fechaIngreso()) : null,
            r.estado(),
            r.idMarca(),
            r.idCategoria()
    );
}


    public void agregarImagen(Integer idProducto, ProductoImagenCreateRequest req) {
        jdbc.update("CALL sp_agregar_imagen(?, ?, ?, ?)",
                req.url(), idProducto, req.portada(), req.galeria());
    }

    public void eliminarImagen(Integer idImagen) {
        jdbc.update("CALL sp_eliminar_imagen(?)", idImagen);
    }

    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM fn_listar_productos()");
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        String sql = "SELECT * FROM fn_listar_productos() WHERE id_producto = ?";
        List<Map<String, Object>> list = jdbc.queryForList(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Map<String, Object>> listarImagenes(Integer idProducto) {
        return jdbc.queryForList("SELECT * FROM fn_listar_imagenes_producto(?)", idProducto);
    }

    public byte[] obtenerImagenContenido(Integer idImagen) {
        return jdbc.queryForObject("SELECT fn_obtener_imagen_blob(?)", byte[].class, idImagen);
    }
}

package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.ProductoCreateRequest;
import com.example.microservicio.microservicio_demo.dto.ProductoImagenCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall insertarProducto;
    private final SimpleJdbcCall agregarImagenCall;
    private final SimpleJdbcCall eliminarImagenCall;

    public ProductoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.insertarProducto = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("insertar_producto")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR),
                        new SqlParameter("p_descripcion", Types.VARCHAR),
                        new SqlParameter("p_precio", Types.NUMERIC),
                        new SqlParameter("p_stock", Types.INTEGER),
                        new SqlParameter("p_descuento", Types.INTEGER),
                        new SqlParameter("p_valoracion", Types.INTEGER),
                        new SqlParameter("p_fecha_ingreso", Types.DATE),
                        new SqlParameter("p_estado", Types.INTEGER),
                        new SqlParameter("p_id_marca", Types.INTEGER),
                        new SqlParameter("p_id_categoria", Types.INTEGER)
                );

        this.agregarImagenCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("sp_agregar_imagen")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_url", Types.VARCHAR),
                        new SqlParameter("p_id_producto", Types.INTEGER),
                        new SqlParameter("p_portada", Types.BOOLEAN),
                        new SqlParameter("p_galeria", Types.BOOLEAN)
                );

        this.eliminarImagenCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("sp_eliminar_imagen")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_imagen", Types.INTEGER)
                );
    }

    public void insertar(ProductoCreateRequest r) {
        var in = new MapSqlParameterSource()
                .addValue("p_nombre", r.nombre())
                .addValue("p_descripcion", r.descripcion())
                .addValue("p_precio", r.precio())
                .addValue("p_stock", r.stock())
                .addValue("p_descuento", r.descuento())
                .addValue("p_valoracion", r.valoracion())
                .addValue("p_fecha_ingreso", r.fechaIngreso())
                .addValue("p_estado", r.estado())
                .addValue("p_id_marca", r.idMarca())
                .addValue("p_id_categoria", r.idCategoria());

        insertarProducto.execute(in);
    }

    public void agregarImagen(Integer idProducto, ProductoImagenCreateRequest req) {
        var in = new MapSqlParameterSource()
                .addValue("p_url", req.url())
                .addValue("p_id_producto", idProducto)
                .addValue("p_portada", req.portada())
                .addValue("p_galeria", req.galeria());
        agregarImagenCall.execute(in);
    }

    public void eliminarImagen(Integer idImagen) {
        var in = new MapSqlParameterSource()
                .addValue("p_id_imagen", idImagen);
        eliminarImagenCall.execute(in);
    }

    public List<Map<String, Object>> listar() {
        return jdbcTemplate.queryForList("SELECT * FROM fn_listar_productos()");
    }

    public List<Map<String, Object>> listarImagenes(Integer idProducto) {
        return jdbcTemplate.queryForList("SELECT * FROM fn_listar_imagenes_producto(?)", idProducto);
    }

    public byte[] obtenerImagenContenido(Integer idImagen) {
        return jdbcTemplate.queryForObject("SELECT fn_obtener_imagen_blob(?)", byte[].class, idImagen);
    }
}

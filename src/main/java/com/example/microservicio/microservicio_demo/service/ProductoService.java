package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.ProductoCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Types;

@Service
public class ProductoService {

    private final SimpleJdbcCall insertarProducto;

    public ProductoService(JdbcTemplate jdbcTemplate) {
        this.insertarProducto = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("insertar_producto")
                // para evitar problemas de metadata, declaramos parámetros:
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR),
                        new SqlParameter("p_descripcion", Types.VARCHAR),
                        new SqlParameter("p_imagen", Types.VARCHAR),
                        new SqlParameter("p_precio", Types.NUMERIC),
                        new SqlParameter("p_stock", Types.INTEGER),
                        new SqlParameter("p_descuento", Types.INTEGER),
                        new SqlParameter("p_valoracion", Types.INTEGER),
                        new SqlParameter("p_fecha_ingreso", Types.DATE),
                        new SqlParameter("p_estado", Types.INTEGER),
                        new SqlParameter("p_id_marca", Types.INTEGER),
                        new SqlParameter("p_id_categoria", Types.INTEGER)
                );
    }

    public void insertar(ProductoCreateRequest r) {
        var in = new MapSqlParameterSource()
                .addValue("p_nombre", r.nombre())
                .addValue("p_descripcion", r.descripcion())
                .addValue("p_imagen", r.imagen())
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
}

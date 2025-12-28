package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.MarcaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service
public class MarcaService {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcCall insertarMarca;

    public MarcaService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;

        this.insertarMarca = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("insertar_marca")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR)
                );
    }

    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM fn_listar_marcas()");
    }

    public void insertar(MarcaCreateRequest req) {
        var in = new MapSqlParameterSource()
                // Si es record:
                .addValue("p_nombre", req.nombre());

        // Si NO es record y es clase con getter, usa:
        // .addValue("p_nombre", req.getNombre());

        insertarMarca.execute(in);
    }
}

package com.handpay.arch.stat.config.repository.jdbc;

import com.handpay.arch.stat.config.model.entity.ConfigEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fczheng on 2016/11/14.
 */
@Repository
public class JdbcRepositoryImpl implements JdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<ConfigEntity> CONFIG_RM = new BeanPropertyRowMapper<ConfigEntity>(ConfigEntity.class);
    private final static String CONFIG_SQL = "SELECT c.id, c.name, c.type, c.table_name tableName, c.description, group_concat(r.kpi_short_name) kpiNames"
            + " FROM config_info c, ref_config_kpi r WHERE c.id = r.config_id GROUP BY r.config_id";

    @Override
    public List<ConfigEntity> findAllConfig() {
        return jdbcTemplate.query(CONFIG_SQL, CONFIG_RM);
    }
}

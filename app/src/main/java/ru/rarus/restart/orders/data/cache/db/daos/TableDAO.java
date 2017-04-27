package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Table;

/**
 * Created by lysmik on 19.12.16.
 */

public class TableDAO extends BaseDaoImpl<Table, Integer> {
    public TableDAO(ConnectionSource connectionSource, Class<Table> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

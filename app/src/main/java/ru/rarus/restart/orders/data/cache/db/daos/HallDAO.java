package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Hall;

/**
 * Created by lysmik on 19.12.16.
 */

public class HallDAO extends BaseDaoImpl<Hall, Integer>{
    public HallDAO(ConnectionSource connectionSource, Class<Hall> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

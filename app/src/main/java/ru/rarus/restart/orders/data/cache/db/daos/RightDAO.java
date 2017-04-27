package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Right;

/**
 * Created by lysmik on 13.01.17.
 */

public class RightDAO extends BaseDaoImpl<Right, Integer> {
    public RightDAO(ConnectionSource connectionSource, Class<Right> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

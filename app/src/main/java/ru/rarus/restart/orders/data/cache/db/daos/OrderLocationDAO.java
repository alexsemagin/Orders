package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.OrderDistance;

/**
 * Created by lysmik on 27.12.16.
 */

public class OrderLocationDAO extends BaseDaoImpl<OrderDistance, Integer> {
    public OrderLocationDAO(ConnectionSource connectionSource, Class<OrderDistance> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

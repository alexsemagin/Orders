package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Order;

/**
 * Created by lysmik on 19.12.16.
 */

public class OrderDAO extends BaseDaoImpl<Order, Integer>{
    public OrderDAO(ConnectionSource connectionSource, Class<Order> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

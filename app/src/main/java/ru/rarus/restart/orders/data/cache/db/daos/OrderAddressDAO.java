package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.OrderAddress;

/**
 * Created by lysmik on 11.01.17.
 */

public class OrderAddressDAO extends BaseDaoImpl<OrderAddress, Integer> {
    public OrderAddressDAO(ConnectionSource connectionSource, Class<OrderAddress> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

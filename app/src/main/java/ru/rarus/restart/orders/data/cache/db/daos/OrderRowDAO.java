package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.OrderRow;

/**
 * Created by lysmik on 19.12.16.
 */

public class OrderRowDAO extends BaseDaoImpl<OrderRow, Integer>{
    public OrderRowDAO(ConnectionSource connectionSource, Class<OrderRow> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

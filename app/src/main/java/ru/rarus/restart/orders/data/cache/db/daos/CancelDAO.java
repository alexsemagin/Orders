package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Cancel;

/**
 * Created by lysmik on 22.12.16.
 */

public class CancelDAO extends BaseDaoImpl<Cancel, Integer> {
    public CancelDAO(ConnectionSource connectionSource, Class<Cancel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

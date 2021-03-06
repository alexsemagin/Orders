package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Guest;

/**
 * Created by lysmik on 19.12.16.
 */

public class GuestDAO extends BaseDaoImpl<Guest, Integer> {
    public GuestDAO(ConnectionSource connectionSource, Class<Guest> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

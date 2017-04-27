package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.MenuItem;

/**
 * Created by lysmik on 22.12.16.
 */

public class MenuItemDAO extends BaseDaoImpl<MenuItem, Integer> {

    public MenuItemDAO(ConnectionSource connectionSource, Class<MenuItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.Menu;

/**
 * Created by lysmik on 19.12.16.
 */

public class MenuDAO extends BaseDaoImpl<Menu, Integer> {
    public MenuDAO(ConnectionSource connectionSource, Class<Menu> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

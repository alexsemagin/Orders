package ru.rarus.restart.orders.data.cache.db.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.entities.DiscCard;

/**
 * Created by lysmik on 19.12.16.
 */

public class DiscCardDAO extends BaseDaoImpl<DiscCard, Integer>{
    public DiscCardDAO(ConnectionSource connectionSource, Class<DiscCard> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

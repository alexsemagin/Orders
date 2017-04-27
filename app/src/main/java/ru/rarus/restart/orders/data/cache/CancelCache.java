package ru.rarus.restart.orders.data.cache;

import android.util.Log;

import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.Cancel;

/**
 * Created by lysmik on 12.01.17.
 */

public class CancelCache {

    private DataBaseHelper dataBaseHelper;

    public CancelCache(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    public void save(List<Cancel> cancels) {
        try {
            TableUtils.clearTable(dataBaseHelper.getConnectionSource(), Cancel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Cancel cancel : cancels) {
            try {
                dataBaseHelper.getCancelDAO().create(cancel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Cancel> getCancels() throws Exception {
        List<Cancel> cancels = dataBaseHelper.getCancelDAO().queryForAll();
        return cancels;
    }

    public Cancel getCancel(String cancelId) throws Exception {
        List<Cancel> cancels = dataBaseHelper.getCancelDAO().queryForEq(Cancel.Column.ID, cancelId);
        Cancel cancel = null;
        if (cancels != null && cancels.size() > 0) {
            cancel = cancels.get(0);
        }
        return cancel;
    }

    public void clearCache() {
        try {
            TableUtils.clearTable(dataBaseHelper.getConnectionSource(), Cancel.class);
        } catch (SQLException e) {
            Log.d("CancelCache","Error " +  e.getMessage());
            e.printStackTrace();
        }
    }
}

package ru.rarus.restart.orders.data.cache.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.rarus.restart.orders.data.cache.db.daos.CancelDAO;
import ru.rarus.restart.orders.data.cache.db.daos.DiscCardDAO;
import ru.rarus.restart.orders.data.cache.db.daos.GuestDAO;
import ru.rarus.restart.orders.data.cache.db.daos.HallDAO;
import ru.rarus.restart.orders.data.cache.db.daos.MenuDAO;
import ru.rarus.restart.orders.data.cache.db.daos.MenuItemDAO;
import ru.rarus.restart.orders.data.cache.db.daos.OperationDAO;
import ru.rarus.restart.orders.data.cache.db.daos.OrderAddressDAO;
import ru.rarus.restart.orders.data.cache.db.daos.OrderDAO;
import ru.rarus.restart.orders.data.cache.db.daos.OrderLocationDAO;
import ru.rarus.restart.orders.data.cache.db.daos.OrderRowDAO;
import ru.rarus.restart.orders.data.cache.db.daos.RightDAO;
import ru.rarus.restart.orders.data.cache.db.daos.TableDAO;
import ru.rarus.restart.orders.data.entities.Cancel;
import ru.rarus.restart.orders.data.entities.DiscCard;
import ru.rarus.restart.orders.data.entities.Guest;
import ru.rarus.restart.orders.data.entities.Hall;
import ru.rarus.restart.orders.data.entities.Menu;
import ru.rarus.restart.orders.data.entities.MenuItem;
import ru.rarus.restart.orders.data.entities.Operation;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.entities.OrderAddress;
import ru.rarus.restart.orders.data.entities.OrderDistance;
import ru.rarus.restart.orders.data.entities.OrderRow;
import ru.rarus.restart.orders.data.entities.Right;
import ru.rarus.restart.orders.data.entities.Table;
import ru.rarus.restart.orders.data.info.InfoStorage;


public class DataBaseHelper extends OrmLiteSqliteOpenHelper {



    private CancelDAO cancelDAO = null;
    private DiscCardDAO discCardDAO = null;
    private GuestDAO guestDAO = null;
    private HallDAO hallDAO = null;
    private MenuDAO menuDAO = null;
    private MenuItemDAO menuItemDAO = null;
    private OperationDAO operationDAO = null;
    private OrderDAO orderDAO = null;
    private OrderLocationDAO orderLocationDAO = null;
    private OrderAddressDAO orderAddressDAO = null;
    private OrderRowDAO orderRowDAO = null;
    private RightDAO rightDAO = null;
    private TableDAO tableDAO = null;

    public DataBaseHelper(Context context, InfoStorage infoStorage, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cancel.class);
            TableUtils.createTable(connectionSource, DiscCard.class);
            TableUtils.createTable(connectionSource, Guest.class);
            TableUtils.createTable(connectionSource, Hall.class);
            TableUtils.createTable(connectionSource, Menu.class);
            TableUtils.createTable(connectionSource, MenuItem.class);
            TableUtils.createTable(connectionSource, Operation.class);
            TableUtils.createTable(connectionSource, Order.class);
            TableUtils.createTable(connectionSource, OrderDistance.class);
            TableUtils.createTable(connectionSource, OrderAddress.class);
            TableUtils.createTable(connectionSource, OrderRow.class);
            TableUtils.createTable(connectionSource, Right.class);
            TableUtils.createTable(connectionSource, Table.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Cancel.class, true);
            TableUtils.dropTable(connectionSource, DiscCard.class, true);
            TableUtils.dropTable(connectionSource, Guest.class, true);
            TableUtils.dropTable(connectionSource, Hall.class, true);
            TableUtils.dropTable(connectionSource, Menu.class, true);
            TableUtils.dropTable(connectionSource, MenuItem.class, true);
            TableUtils.dropTable(connectionSource, Operation.class, true);
            TableUtils.dropTable(connectionSource, Order.class, true);
            TableUtils.dropTable(connectionSource, OrderDistance.class, true);
            TableUtils.dropTable(connectionSource, OrderAddress.class, true);
            TableUtils.dropTable(connectionSource, OrderRow.class, true);
            TableUtils.dropTable(connectionSource, Right.class, true);
            TableUtils.dropTable(connectionSource, Table.class, true);
            onCreate(database, connectionSource);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean clearTable(){
        Log.d("QQQ","thread" +Thread.currentThread());
        try {
            TableUtils.clearTable(connectionSource, Cancel.class);
            TableUtils.clearTable(connectionSource, DiscCard.class);
            TableUtils.clearTable(connectionSource, Guest.class);
            TableUtils.clearTable(connectionSource, Hall.class);
            TableUtils.clearTable(connectionSource, Menu.class);
            TableUtils.clearTable(connectionSource, MenuItem.class);
            TableUtils.clearTable(connectionSource, Order.class);
            TableUtils.clearTable(connectionSource, OrderDistance.class);
            TableUtils.clearTable(connectionSource, OrderAddress.class);
            TableUtils.clearTable(connectionSource, OrderRow.class);
            TableUtils.clearTable(connectionSource, Right.class);
            TableUtils.clearTable(connectionSource, Table.class);
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }

    public CancelDAO getCancelDAO() throws SQLException {
        if (cancelDAO == null) {
            cancelDAO = new CancelDAO(getConnectionSource(), Cancel.class);
        }
        return  cancelDAO;
    }

    public DiscCardDAO getDiscCardDAO() throws SQLException {
        if (discCardDAO == null) {
            discCardDAO = new DiscCardDAO(getConnectionSource(), DiscCard.class);
        }
        return discCardDAO;
    }

    public GuestDAO getGuestDAO() throws SQLException {
        if (guestDAO == null) {
            guestDAO = new GuestDAO(getConnectionSource(), Guest.class);
        }
        return guestDAO;
    }

    public HallDAO getHallDAO() throws SQLException {
        if (hallDAO == null) {
            hallDAO = new HallDAO(getConnectionSource(), Hall.class);
        }
        return hallDAO;
    }

    public MenuDAO getMenuDAO() throws SQLException {
        if (menuDAO == null) {
            menuDAO = new MenuDAO(getConnectionSource(), Menu.class);
        }
        return menuDAO;
    }

    public MenuItemDAO getMenuItemDAO() throws SQLException {
        if (menuItemDAO == null) {
            menuItemDAO = new MenuItemDAO(getConnectionSource(), MenuItem.class);
        }
        return menuItemDAO;
    }

    public OperationDAO getOperationDAO() throws SQLException {
        if (operationDAO == null) {
            operationDAO = new OperationDAO(getConnectionSource(), Operation.class);
        }
        return operationDAO;
    }

    public OrderDAO getOrderDAO() throws SQLException {
        if (orderDAO == null) {
            orderDAO = new OrderDAO(getConnectionSource(), Order.class);
        }
        return orderDAO;
    }

    public OrderLocationDAO getOrderLocationDAO() throws SQLException {
        if (orderLocationDAO == null) {
            orderLocationDAO = new OrderLocationDAO(getConnectionSource(), OrderDistance.class);
        }
        return orderLocationDAO;
    }

    public OrderAddressDAO getOrderAddressDAO() throws SQLException {
        if (orderAddressDAO == null) {
            orderAddressDAO = new OrderAddressDAO(getConnectionSource(), OrderAddress.class);
        }
        return orderAddressDAO;
    }

    public OrderRowDAO getOrderRowDAO() throws SQLException {
        if (orderRowDAO == null) {
            orderRowDAO = new OrderRowDAO(getConnectionSource(), OrderRow.class);
        }
        return orderRowDAO;
    }

    public RightDAO getRightDAO() throws SQLException {
        if (rightDAO == null) {
            rightDAO = new RightDAO(getConnectionSource(), Right.class);
        }
        return rightDAO;
    }

    public TableDAO getTableDAO() throws SQLException {
        if (tableDAO == null) {
            tableDAO = new TableDAO(getConnectionSource(), Table.class);
        }
        return tableDAO;
    }

    @Override
    public void close() {
        super.close();
        cancelDAO = null;
        discCardDAO = null;
        guestDAO = null;
        hallDAO = null;
        menuDAO = null;
        menuItemDAO = null;
        operationDAO = null;
        orderDAO = null;
        orderAddressDAO = null;
        orderLocationDAO = null;
        orderRowDAO = null;
        rightDAO = null;
        tableDAO = null;
    }
}

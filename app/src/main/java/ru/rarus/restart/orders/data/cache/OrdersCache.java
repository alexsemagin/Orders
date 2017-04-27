package ru.rarus.restart.orders.data.cache;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.entities.OrderAddress;
import ru.rarus.restart.orders.data.entities.OrderDistance;
import ru.rarus.restart.orders.data.entities.OrderRow;


public class OrdersCache {

    private DataBaseHelper mDataBaseHelper;


    public OrdersCache(DataBaseHelper dataBaseHelper) {
        this.mDataBaseHelper = dataBaseHelper;

    }

    public List<Order> getFreeOrders() {
        return getOrdersFromDB(true, Order.STATUS.FREE);
    }

    public List<Order> getMyOrders() {
        List<Order> list = getOrdersFromDB(true, Order.STATUS.IN_WORK);
        return sortMyOrder(list);
    }

    public void saveCloseOrderBD(Order order) {
        order.setStatus(Order.STATUS.SUCCESS);
        order.setStatus(Order.STATUS.CLOSE);
        saveOrderBD(order);
    }

    public List<Order> getSuccessOrders() {
        return getOrdersFromDB(false, Order.STATUS.SUCCESS);
    }

    public List<Order> saveListOrder(List<Order> list, boolean isFree) {
        return  saveListOrder(list, isFree, Order.STATUS.IN_WORK);
    }
    public  List<Order> saveListSuccessOrders(List<Order> list) {
        return  saveListOrder(list, false, Order.STATUS.SUCCESS);
    }

    private List<Order> sortMyOrder(List<Order> list){
        //Collections.sort(list, (a, b) -> a.getLocation().getIndex().compareTo(b.getLocation().getIndex()));
        int i=0;
        for (Order c : list) {
            if(c.getLocation().getIndex() != i) {
                c.getLocation().setIndex(i);
                saveLocation(c.getLocation());
            }
            i++;
        }
        return list;
    }

    private  List<Order> saveListOrder(List<Order> list, boolean isFree, int status) {
        try {
            List<Order> listDB = mDataBaseHelper.getOrderDAO().queryBuilder()
                    .where()
                    .eq(Order.Column.IS_MY, isFree)
                    .and()
                    .eq(Order.Column.STATUS, status)
                    .query();

            mDataBaseHelper.getOrderDAO().delete(listDB);


            for (Order c : list) {
                c.setMy(isFree);
                c.setStatus(status);
                c.setTotalSum();
                mDataBaseHelper.getOrderDAO().createOrUpdate(c);

                List<OrderRow> listRow = mDataBaseHelper.getOrderRowDAO().queryForEq(OrderRow.Column.PARENT_ID, c.getId());
                mDataBaseHelper.getOrderRowDAO().delete(listRow);
                for (OrderRow row : c.getOrderRows()) {
                    row.setParentId(c.getId());
                    mDataBaseHelper.getOrderRowDAO().create(row);
                }

                List<OrderAddress> listAddresses = mDataBaseHelper.getOrderAddressDAO().queryForEq(OrderAddress.Column.ORDER_ID, c.getId());
                mDataBaseHelper.getOrderAddressDAO().delete(listAddresses);
                mDataBaseHelper.getOrderAddressDAO().create(c.getOrderAddress());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sortMyOrder(list);
    }


    private List<Order> getOrdersFromDB(boolean isFree, int status) {
        List<Order> list;
        try {
            list = mDataBaseHelper.getOrderDAO().queryBuilder()
                    .where()
                    .eq(Order.Column.IS_MY, isFree)
                    .and()
                    .eq(Order.Column.STATUS, status)
                    .query();
            for (Order c : list) {
                List<OrderAddress> listAddresses = mDataBaseHelper.getOrderAddressDAO().queryForEq(OrderAddress.Column.ORDER_ID, c.getId());
                if (listAddresses.size() > 0) c.setOrderAddress(listAddresses.get(0));

                List<OrderDistance> location = mDataBaseHelper.getOrderLocationDAO().queryForEq(OrderDistance.Column.ORDER_ID, c.getId());
                if (location.size() > 0) c.setLocation(location.get(0));
                else{
                    OrderDistance orderDistance = new OrderDistance();
                    orderDistance.setOrderId(c.getId());
                    c.setLocation(orderDistance);
                    saveLocation(orderDistance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }


        return list;
    }


    public Order getOrderFromDB(String idOrder) {
        Order order = null;
        try {
            List<Order> list = mDataBaseHelper.getOrderDAO().queryForEq(Order.Column.ORDER_ID, idOrder);
            if (list.size() > 0) {
                order = list.get(0);
                order.setOrderRows(mDataBaseHelper.getOrderRowDAO().queryForEq(OrderRow.Column.PARENT_ID, order.getId()));

                List<OrderAddress> listAddresses = mDataBaseHelper.getOrderAddressDAO().queryForEq(OrderAddress.Column.ORDER_ID, order.getId());
                if (listAddresses.size() > 0) order.setOrderAddress(listAddresses.get(0));

                List<OrderDistance> location = mDataBaseHelper.getOrderLocationDAO().queryForEq(OrderDistance.Column.ORDER_ID, order.getId());
                if (location.size() > 0) order.setLocation(location.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public int saveOrderBD(Order order)  {
        try {
            mDataBaseHelper.getOrderDAO().createOrUpdate(order);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public void removeOrderBD(String idOrder) {
        try {
            List<Order> list = mDataBaseHelper.getOrderDAO().queryForEq(Order.Column.ORDER_ID, idOrder);
            if (list.size() > 0) mDataBaseHelper.getOrderDAO().delete(list.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Order saveLocationOrder(Order order) {
        try {
            mDataBaseHelper.getOrderLocationDAO().createOrUpdate(order.getLocation());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void initLocationOrder(Order order) {
        try {
            List<OrderDistance> location = mDataBaseHelper.getOrderLocationDAO().queryForEq(OrderDistance.Column.ORDER_ID, order.getId());
            if (location.size() > 0) order.setLocation(location.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<OrderDistance> getLocationsOrder() {
        List<OrderDistance> locations;
        try {
            locations = mDataBaseHelper.getOrderLocationDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            locations = new ArrayList<>();
        }
        return locations;
    }


    public void saveLocation(OrderDistance location) {
        try {
            mDataBaseHelper.getOrderLocationDAO().createOrUpdate(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public OrderDistance getLocationByIdOrder(String idOrder) {
        try {
            List<OrderDistance> items = mDataBaseHelper.getOrderLocationDAO().queryForEq(OrderDistance.Column.ORDER_ID, idOrder);
            if(items.size()>0) return items.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}

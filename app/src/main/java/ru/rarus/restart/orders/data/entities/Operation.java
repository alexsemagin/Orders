package ru.rarus.restart.orders.data.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import ru.rarus.restart.orders.R;


@DatabaseTable(tableName = "Operation")
public class Operation {
    public static class Column {
        public final static String DELIVERY_ID = "deliveryId";
        private final static String OPERATION_ID = "operationId";
        private final static String TIME = "time";
        private final static String ACTION = "action";
        private final static String ORDER_ID = "orderId";
        private final static String MESSAGE = "message";
    }

    public static class Action {
        public final static String ACTION_SIGN_IN = "login";
        public final static String ACTION_SIGN_OUT = "loginOut";
        public final static String ACTION_ORDER_ATTACH = "orderAttach";
        public final static String ACTION_ORDER_DETACH = "orderDetach";
        public final static String ACTION_ORDER_CANCELED = "orderCancel";
        public final static String ACTION_ORDER_SUCCESS = "orderSuccess";
        public final static String ACTION_PUT_CASH = "putCashInCashBox";
    }

    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Operation.Column.OPERATION_ID)
    private String id;

    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_ID)
    private String idDelivery;

    @DatabaseField(dataType = DataType.DATE, columnName = Operation.Column.TIME)
    private Date time;

    @DatabaseField(dataType = DataType.STRING, columnName = Operation.Column.ACTION)
    private String action;

    @DatabaseField(dataType = DataType.STRING, columnName = Operation.Column.ORDER_ID)
    private String orderId;

    @DatabaseField(dataType = DataType.STRING, columnName = Operation.Column.MESSAGE)
    private String message;

    private Order order;


    public String getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(String idDelivery) {
        this.idDelivery = idDelivery;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStringIdAction() {
        switch (action) {
            case Operation.Action.ACTION_SIGN_IN:
                return R.id.sort_by_action_sign_in;
            case Operation.Action.ACTION_SIGN_OUT:
                return R.id.sort_by_action_sign_out;
            case Operation.Action.ACTION_ORDER_ATTACH:
                return R.id.sort_by_action_order_attach;
            case Operation.Action.ACTION_ORDER_DETACH:
                return R.id.sort_by_action_order_detach;
            case Operation.Action.ACTION_ORDER_SUCCESS:
                return R.id.sort_by_action_order_success;
            case Operation.Action.ACTION_ORDER_CANCELED:
                return R.id.sort_by_action_order_canceled;
            case Operation.Action.ACTION_PUT_CASH:
                return R.id.sort_by_action_put_cash;
        }
         return R.id.sort_by_all_actions;
    }
}

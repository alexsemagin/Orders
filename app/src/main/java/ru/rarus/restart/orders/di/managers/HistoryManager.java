package ru.rarus.restart.orders.di.managers;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.Operation;
import ru.rarus.restart.orders.data.entities.Order;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HistoryManager extends BaseManager {

    private String idUser = "";

    public HistoryManager(Context mContext, DataBaseHelper mDataBaseHelper, RxBus mRxBus) {
        super(mContext, mDataBaseHelper, mRxBus);

    }


    public Observable<List<Operation>> getOperationList() {
        return Observable.just(readOperationFromDB())
                .flatMap(Observable::from)
                .toSortedList((u1, u2) -> (u1.getTime().compareTo(u2.getTime())) * -1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    void createOrderOperation(Order order, String action) {
        Log.d("QQQ", "createOrderOperation " + idUser);
        Operation operation = new Operation();
        UUID uuid = UUID.randomUUID();
        operation.setId(String.valueOf(uuid));
        operation.setIdDelivery(idUser);
        operation.setAction(action);
        operation.setMessage("");
        operation.setOrderId(getDescription(order));
        operation.setTime(Calendar.getInstance().getTime());

        save(operation);
    }


    public void createStringOperation(String message, String action) {
        Log.d("QQQ", "createStringOperation " + idUser);
        Operation operation = new Operation();
        UUID uuid = UUID.randomUUID();
        operation.setId(String.valueOf(uuid));
        operation.setIdDelivery(idUser);
        operation.setAction(action);
        operation.setOrderId("");
        operation.setMessage(message);
        operation.setTime(Calendar.getInstance().getTime());

        save(operation);
    }


    private List<Operation> readOperationFromDB() {
        List<Operation> listOperation;
        try {
            Log.d("QQQ", "mRestartSettings.getUserId() " + idUser);
            listOperation = mDataBaseHelper.getOperationDAO().queryForEq(Operation.Column.DELIVERY_ID, idUser);
        } catch (SQLException e) {
            e.printStackTrace();
            listOperation = new ArrayList<>();
        }
        return listOperation;
    }

    private void save(Operation operation) {

        try {
            mDataBaseHelper.getOperationDAO().create(operation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDescription(Order order) {
        return order == null ? "" : order.getNumString() + "  " + order.getOrderAddress().getGeoAddressStreetAndHouse();
    }

    void setUserId(String id) {
        idUser = id;
    }
}

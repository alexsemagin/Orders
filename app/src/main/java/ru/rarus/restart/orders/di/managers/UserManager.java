package ru.rarus.restart.orders.di.managers;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;


import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.Right;
import ru.rarus.restart.orders.data.entities.User;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.retrofit.RestAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_ATTACH;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_CANCELED;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_DETACH;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_ORDER_SUCCESS;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_PUT_CASH;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_SIGN_IN;
import static ru.rarus.restart.orders.data.entities.Operation.Action.ACTION_SIGN_OUT;

public class UserManager extends BaseManager {

    User mUser;
    private InfoStorage infoStorage;
    private HistoryManager historyManager;
    private RestAPI restAPI;

    public UserManager(Context mContext, InfoStorage infoStorage, DataBaseHelper dataBaseHelper, RxBus mRxBus, HistoryManager historyManager, RestAPI restAPI) {
        super(mContext, dataBaseHelper, mRxBus);
        this.infoStorage = infoStorage;
        this.historyManager = historyManager;
        this.restAPI = restAPI;
    }

    public User getUser() {
        if (mUser == null) {
            mUser = infoStorage.getUser();
            historyManager.setUserId(mUser.getId());
            try {
                List<Right> rights = mDataBaseHelper.getRightDAO().queryForEq(Right.Column.USER_ID, mUser.getId());
                mUser.setRights(rights);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return mUser;
    }

    public void singIn() {
        historyManager.createStringOperation(getUser().getName(), ACTION_SIGN_IN);
        //test
        historyManager.createStringOperation(getUser().getName(), ACTION_SIGN_OUT);
        historyManager.createStringOperation(getUser().getName(), ACTION_ORDER_ATTACH);
        historyManager.createStringOperation(getUser().getName(), ACTION_ORDER_DETACH);
        historyManager.createStringOperation(getUser().getName(), ACTION_ORDER_CANCELED);
        historyManager.createStringOperation(getUser().getName(), ACTION_PUT_CASH);
        historyManager.createStringOperation(getUser().getName(), ACTION_ORDER_SUCCESS);
        //test
    }

    public void singOut() {

        restAPI.signOut("1221132")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                }, e -> {
                });

        historyManager.createStringOperation(getUser().getName(), ACTION_SIGN_OUT);
        Observable.defer(() -> Observable.just(mDataBaseHelper.clearTable()))
                .subscribeOn(Schedulers.io())
                .subscribe(r -> {
                }, e -> {
                });

    }
}

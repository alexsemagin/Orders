package ru.rarus.restart.orders.di.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;


import ru.rarus.restart.orders.data.entities.Right;
import ru.rarus.restart.orders.data.entities.User;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.retrofit.RestAPI;
import ru.rarus.restart.orders.data.retrofit.dto.BaseResponse;
import ru.rarus.restart.orders.data.retrofit.dto.SignInRequest;
import ru.rarus.restart.orders.data.retrofit.dto.SignInRestResponse;
import ru.rarus.restart.orders.data.retrofit.dto.UserRestResponse;

import ru.rarus.restart.orders.di.module.utils.HashSHA1;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SyncManager {

    private final InfoStorage infoStorage;
    private RestAPI restAPI;
    private DataBaseHelper mDataBaseHelper;
    private Context context;


    public SyncManager(Context context, RestAPI restAPI, DataBaseHelper dataBaseHelper, InfoStorage infoStorage) {
        this.context = context;
        this.restAPI = restAPI;
        this.infoStorage = infoStorage;
        this.mDataBaseHelper = dataBaseHelper;

    }

    public Observable<Boolean> signIn(String password) {


        return restAPI
                .signIn(HashSHA1.SHA1(password),android.os.Build.MODEL)
                .map(response -> {if(!response.isSuccess())Observable.error(new Throwable(response.getMessage())); return response;})
                .map(response -> {
                    infoStorage.setUser(response.getUser());
                    return true;
                })
                .flatMap(res -> createUserComponent())
                .onErrorResumeNext(exp -> Observable.error(new Throwable(getError(exp))))
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    private Observable<Boolean> createUserComponent() {
        MyApp.getApp().CreateUserComponent();
        return Observable.just(true);
    }



//    private Observable<Boolean> loadUser() {
//        return restAPI.getUser()
//                .map(UserRestResponse::getUser)
//                .doOnNext(this::save)
//                .map(r -> true)
//                .subscribeOn(Schedulers.io());
//    }

    private void save(User user) {
        infoStorage.setUser(user);

        try {
            List<Right> list = mDataBaseHelper.getRightDAO().queryForEq(Right.Column.USER_ID,user.getId());
            if(list.size()>0)  mDataBaseHelper.getRightDAO().delete(list);

            for (Right right : user.getRights()) {
                right.setUserId(user.getId());

                mDataBaseHelper.getRightDAO().create(right);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String getError(Throwable throwable) {


        if (throwable instanceof ConnectException) {
            if (!isNetworkOnline(context)) {
                return "Нет подключения";
            } else {
                return "Ошибка связи с сервером";
            }
        }

        if (throwable instanceof UnknownHostException) {

            if (!isNetworkOnline(context)) {
                return "Нет подключения";
            } else {
                return "Ошибка связи с сервером";
            }


        } else if (throwable instanceof SocketTimeoutException) {
            return "Ошибка связи с сервером";

        } else if (throwable instanceof HttpException) {

            HttpException responseException = ((HttpException) throwable);
            if (responseException != null && responseException.code() == 404) {

                ResponseBody responseBody = responseException.response().errorBody();

                JSONObject jsonObject;

                try {
                    jsonObject = new JSONObject(responseBody.string());
                    String codeStr = String.valueOf(jsonObject.get("code"));
                    int code = Integer.valueOf(codeStr);
                    return String.valueOf(jsonObject.get("error"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (responseException != null && responseException.code() == 400) {

                ResponseBody responseBody = responseException.response().errorBody();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseBody.string());

                    return String.valueOf(jsonObject.get("error"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } else {
            return throwable.getMessage();
        }
        return throwable.getMessage();
    }

    private static boolean isNetworkOnline(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }


    public void createDemo() {
        infoStorage.setPassword("1234");
        createUserComponent();
    }
}

package ru.rarus.restart.orders.di.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import ru.rarus.restart.orders.data.retrofit.RestAPI;

public class ErrorsManager {

    Context ctx;
    Retrofit mRetrofit;
    RestAPI restApi;
    SyncManager syncManager;


    public ErrorsManager(Context ctx, RestAPI restAPI, SyncManager syncManager) {
        this.ctx = ctx;
        this.restApi = restAPI;
        this.syncManager =syncManager;
    }


//    private Boolean updateToken() {
//        return syncManager.signIn().toBlocking().first();
//    }



    public  Error getError(Throwable throwable) {


        if (throwable instanceof ConnectException) {
            if (!isNetworkOnline(ctx)) {
                return new Error(800,"Нет подключения");
            } else {
                return new Error(0,"Ошибка связи с сервером");
            }
        }

        if (throwable instanceof UnknownHostException) {

            if (!isNetworkOnline(ctx)) {
                return new Error(800,"Нет подключения");
            } else {
                return new Error(0,"Ошибка связи с сервером");
            }


        } else if (throwable instanceof SocketTimeoutException) {
            return new Error(0,"Ошибка связи с сервером");

        } else if (throwable instanceof HttpException) {

            HttpException responseException = ((HttpException) throwable);
            if (responseException != null && responseException.code() == 404) {

                ResponseBody responseBody = responseException.response().errorBody();

                JSONObject jsonObject;

                try {
                    jsonObject = new JSONObject(responseBody.string());
                    String codeStr = String.valueOf(jsonObject.get("code"));
                    int code =  Integer.valueOf(codeStr);
                    return new Error(code,String.valueOf(jsonObject.get("message")));
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
                    String codeStr = String.valueOf(jsonObject.get("code"));
                    String mess = String.valueOf(jsonObject.get("message"));

                    int code =  Integer.valueOf(codeStr);

//                    if (code == 7  &&  updateToken()) {
//                        return new Error(101,"Token updated");
//                    }
                    return new Error(code,mess);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } else {
            return new Error(0,throwable.getMessage());
        }
        return new Error(0,throwable.getMessage());
    }


    public  class Error{
        public int code = 0;
        public String description = "";

        public Error(int code, String description) {
            this.code = code;
            this.description = description;
        }
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

}

package ru.rarus.restart.orders.data.cache.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;


public class HelperFactory {

    private static DataBaseHelper dataBaseHelper;

    public static DataBaseHelper getDataBaseHelper() {
        return  dataBaseHelper;
    }

    public static void setDataBaseHelper(Context context) {
        dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        dataBaseHelper = null;
    }
}

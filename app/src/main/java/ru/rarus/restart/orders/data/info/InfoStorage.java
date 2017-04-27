package ru.rarus.restart.orders.data.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.rarus.restart.orders.data.entities.User;
import ru.rarus.restart.orders.data.settings.RestartSettings;


public class InfoStorage {


    public static DecimalFormat dfMoney  = new DecimalFormat("###,###.##"+" "+ RestartSettings.sCurrency);;
    public static DecimalFormat dfMoneyLight = new DecimalFormat("###,##0.0");
    public static DecimalFormat dfPercent = new DecimalFormat("##" + "");
    public static DecimalFormat dfCount = new DecimalFormat("###,###.#");
    public static DecimalFormat dfDuration = new DecimalFormat("###,###");
    public static NumberFormat nfLocation = new DecimalFormat("#0.000000");

    public static SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm, dd MMM", Locale.getDefault());
    public static SimpleDateFormat formatDateLight = new SimpleDateFormat("HH:mm", Locale.getDefault());


    private final SharedPreferences mSp;
    private static final String PREFS_NAME = "InfoStorage";

    private static final String DATABASE_NAME = "ru.rarus.restart.orders.database";
    private static final int DATABASE_VERSION = 17;

    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_PASSWD = "KEY_PASSWD";
    private static final String DEFAULT_PASSWD = "7841";

    public static final String DEMO_PASSWD = "1313";

    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_PHONE = "KEY_USER_PHONE";

    public InfoStorage(Context context) {
        mSp = context.getSharedPreferences(PREFS_NAME, 0);

    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public String getToken() {
        return mSp.getString(KEY_TOKEN, "");
    }

    public void setToken(String token) {
        mSp.edit().putString(KEY_TOKEN, token).commit();
    }

    public String getPassword() {
        return mSp.getString(KEY_PASSWD, DEFAULT_PASSWD);
    }

    public void setPassword(String password) {
        mSp.edit().putString(KEY_PASSWD, password).commit();
    }

    public void setUser(User user) {
        Log.d("QQQ","setUser "+user.getId());
        mSp.edit().putString(KEY_USER_ID, user.getId()).apply();
        mSp.edit().putString(KEY_USER_NAME, user.getName()).apply();
        mSp.edit().putString(KEY_USER_PHONE, user.getPhone()).apply();
    }

    public User getUser() {
        User user = new User();
        user.setId(mSp.getString(KEY_USER_ID, ""));
        user.setName(mSp.getString(KEY_USER_NAME, ""));
        user.setPhone(mSp.getString(KEY_USER_PHONE, ""));
        return user;
    }
}

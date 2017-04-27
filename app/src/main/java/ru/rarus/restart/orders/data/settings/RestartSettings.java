package ru.rarus.restart.orders.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

public class RestartSettings {

    private static final String PREFS_NAME = "SoapPref";

    private static final String DEFAULT_OPERATOR_PHONE_NUMBER = "+74952312002";
    private static final String OPERATOR_PHONE_NUMBER_WITH_BRACKETS = "+7(495)2312002";
    private static final String KEY_OPERATOR_PHONE_NUMBER = "OPERATOR_PHONE_NUMBER";
    private static final String KEY_OPERATOR_PHONE_NUMBER_WITH_BRACKETS =
            "OPERATOR_PHONE_NUMBER_WITH_BRACKETS";
    private static final String KEY_SERVER_ADDRESS = "SERVER_ADDRESS";
    private static final String DEFAULT_SERVER_ADDRESS = "http://192.168.38.248/demo/hs/";
    private static final String KEY_ALTERNATIVE_SERVER = "ALTERNATIVE_SERVER";
    private static final boolean DEFAULT_ALTERNATIVE_SERVER = false;
    private static final String KEY_ALTERNATIVE_SERVER_ADDRESS = "ALTERNATIVE_SERVER_ADDRESS";

    private static final String DEFAULT_ALTERNATIVE_SERVER_ADDRESS = "http://mworker.rarus-cloud.ru/base/hs/exchangemobile/";
    private static final String KEY_DEVICE_NAME = "DEVICE_NAME";
    private static final String DEFAULT_DEVICE_NAME = String.valueOf(Build.MODEL);
    private static final String KEY_UPDATE_PERIOD = "UPDATE_PERIOD";
    private static final int DEFAULT_UPDATE_PERIOD = 1;

    private static final String KEY_CARD_SCAN_MODE = "CARD_SCAN_MODE";
    private static final int DEFAULT_CARD_SCAN_MODE = 1;
    private static final String KEY_CHECK_MENU_PRICE = "CHECK_MENU_PRICE";
    private static final boolean DEFAULT_CHECK_MENU_PRICE = true;
    private static final String KEY_ASK_FOR_FURTHER_ACTION = "ASK_FOR_FURTHER_ACTION";
    private static final boolean DEFAULT_ASK_FOR_FURTHER_ACTION = true;
    private static final String KEY_ASK_THE_GUEST_NUMBER = "ASK_THE_GUEST_NUMBER";
    private static final boolean DEFAULT_ASK_THE_GUEST_NUMBER = true;
    private static final String KEY_CURSED_WORK = "CURSED_WORK";
    private static final boolean DEFAULT_CURSED_WORK = true;
    private static final String KEY_DELAY_STEP_ON_THE_DISH = "DELAY_STEP_ON_THE_DISH";
    private static final int DEFAULT_DELAY_STEP_ON_THE_DISH = 1;
    private static final String KEY_STEP_DELAY_ON_THE_COURSE = "STEP_DELAY_ON_THE_COURSE";
    private static final int DEFAULT_STEP_DELAY_ON_THE_COURSE = 1;

    private static final String KEY_ORGANIZATION_NAME = "ORGANIZATION_NAME";
    private static final String DEFAULT_ORGANIZATION_NAME = "1С-Рарус";
    private static final String KEY_CURRENCY = "CURRENCY";
    private static final String DEFAULT_CURRENCY = "руб";

    private static final String KEY_DEPTH_DISPLAY_MESSAGE = "DEPTH_DISPLAY_MESSAGE";
    private static final int DEFAULT_DEPTH_DISPLAY_MESSAGE = 3;
    private static final String KEY_NEW_POST_MELODY = "KEY_NEW_POST_MELODY";
    private static final String DEFAULT_NEW_POST_MELODY = "";

    private static final String LATITUDE_DP = "LATITUDE_DP";
    private static final String LONGITUDE_DP = "LONGITUDE_DP";
    private static final String ADRESS_DP = "ADRESS_DP";

    private static final String SUM_IN_CASHBOX = "SUM_IN_CASHBOX";


    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    public static String sCurrency;

    public RestartSettings(Context context) {
        mSettings = context.getSharedPreferences(PREFS_NAME, 0);
        sCurrency = getCurrency();
    }


    private void editPrefs(Object val, String key) {
        String type = val.getClass().getSimpleName();
        mEditor = mSettings.edit();
        if (type.equals(String.class.getSimpleName())) {
            mEditor.putString(key, (String) val);
        } else if (type.equals(Integer.class.getSimpleName())) {
            mEditor.putInt(key, (Integer) val);
        } else if (type.equals(Boolean.class.getSimpleName())) {
            mEditor.putBoolean(key, (Boolean) val);
        } else if (type.equals(Float.class.getSimpleName())) {
            mEditor.putFloat(key, (Float) val);
        }

        mEditor.apply();

    }




    public String getServerAddress() {
        return mSettings.getString(KEY_SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
    }

    public boolean isAlternativeServer() {
        return mSettings.getBoolean(KEY_ALTERNATIVE_SERVER, DEFAULT_ALTERNATIVE_SERVER);
    }

    public String getCurrentServerAddress() {
        if (mSettings.getBoolean(KEY_ALTERNATIVE_SERVER, DEFAULT_ALTERNATIVE_SERVER)) {
            return mSettings.getString(KEY_ALTERNATIVE_SERVER_ADDRESS, DEFAULT_ALTERNATIVE_SERVER_ADDRESS);
        } else {
            return mSettings.getString(KEY_SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
        }
    }

    public String getAlternativeServerAddress() {
        return mSettings.getString(KEY_ALTERNATIVE_SERVER_ADDRESS, DEFAULT_ALTERNATIVE_SERVER_ADDRESS);
    }

    public String getDeviceName() {
        return mSettings.getString(KEY_DEVICE_NAME, DEFAULT_DEVICE_NAME);
    }

    public int getUpdatePeriod() {
        return mSettings.getInt(KEY_UPDATE_PERIOD, DEFAULT_UPDATE_PERIOD);
    }

    public boolean isCheckMenuPrice() {
        return mSettings.getBoolean(KEY_CHECK_MENU_PRICE, DEFAULT_CHECK_MENU_PRICE);
    }

    public boolean isAskForFurtherAction() {
        return mSettings.getBoolean(KEY_ASK_FOR_FURTHER_ACTION, DEFAULT_ASK_FOR_FURTHER_ACTION);
    }

    public boolean isAskTheGuestNumber() {
        return mSettings.getBoolean(KEY_ASK_THE_GUEST_NUMBER, DEFAULT_ASK_THE_GUEST_NUMBER);
    }

    public boolean isCoursedWork() {
        return mSettings.getBoolean(KEY_CURSED_WORK, DEFAULT_CURSED_WORK);
    }

    public int getDelayStepOnTheDish() {
        return mSettings.getInt(KEY_DELAY_STEP_ON_THE_DISH, DEFAULT_DELAY_STEP_ON_THE_DISH);
    }

    public int getStepDelayOnTheCourse() {
        return mSettings.getInt(KEY_STEP_DELAY_ON_THE_COURSE, DEFAULT_STEP_DELAY_ON_THE_COURSE);
    }

    public String getOrganizationName() {
        return mSettings.getString(KEY_ORGANIZATION_NAME, DEFAULT_ORGANIZATION_NAME);
    }

    public String getCurrency() {
        return mSettings.getString(KEY_CURRENCY, DEFAULT_CURRENCY);
    }

    public int getDepthDisplayMessages() {
        return mSettings.getInt(KEY_DEPTH_DISPLAY_MESSAGE, DEFAULT_DEPTH_DISPLAY_MESSAGE);
    }

    public String getNewPostMelody() {
        return mSettings.getString(KEY_NEW_POST_MELODY, DEFAULT_NEW_POST_MELODY);
    }

    public int getCardScanMode() {
        return mSettings.getInt(KEY_CARD_SCAN_MODE, DEFAULT_CARD_SCAN_MODE);
    }

    public String getOperatorPhoneNumber() {
        return mSettings.getString(KEY_OPERATOR_PHONE_NUMBER, DEFAULT_OPERATOR_PHONE_NUMBER);
    }

    public String getOperatorPhoneNumberWithBrackets() {
        return mSettings.getString(KEY_OPERATOR_PHONE_NUMBER_WITH_BRACKETS,
                OPERATOR_PHONE_NUMBER_WITH_BRACKETS);
    }

    public void setCardScanMode(int cardScanMode) {
        editPrefs(cardScanMode, KEY_CARD_SCAN_MODE);
    }

    public void setServerAddress(String serverAddress) {
        //validation
        editPrefs(serverAddress, KEY_SERVER_ADDRESS);
    }

    public void setAlternativeServer(boolean alternativeServer) {
        editPrefs(alternativeServer, KEY_ALTERNATIVE_SERVER);
    }

    public void setAlternativeServerAddress(String alternativeServerAddress) {
        editPrefs(alternativeServerAddress, KEY_ALTERNATIVE_SERVER_ADDRESS);
    }

    public void setDeviceName(String deviceName) {
        editPrefs(deviceName, KEY_DEVICE_NAME);
    }

    public void setUpdatePeriod(int updatePeriod) {
        editPrefs(updatePeriod, KEY_UPDATE_PERIOD);
    }

    public void setCheckMenuPrice(boolean checkMenuPrice) {
        editPrefs(checkMenuPrice, KEY_CHECK_MENU_PRICE);
    }

    public void setAskForFurtherAction(boolean askForFurtherAction) {
        editPrefs(askForFurtherAction, KEY_ASK_FOR_FURTHER_ACTION);
    }

    public void setAskTheGuestNumber(boolean askTheGuestNumber) {
        editPrefs(askTheGuestNumber, KEY_ASK_THE_GUEST_NUMBER);
    }

    public void setCoursedWork(boolean coursedWork) {
        editPrefs(coursedWork, KEY_CURSED_WORK);
    }

    public void setDelayStepOnTheDish(int delayStepOnTheDish) {
        editPrefs(delayStepOnTheDish, KEY_DELAY_STEP_ON_THE_DISH);
    }

    public void setStepDelayOnTheCourse(int stepDelayOnTheCourse) {
        editPrefs(stepDelayOnTheCourse, KEY_STEP_DELAY_ON_THE_COURSE);
    }

    public void setOrganizationName(String organizationName) {
        editPrefs(organizationName, KEY_ORGANIZATION_NAME);
    }

    public void setCurrency(String currency) {
        editPrefs(currency, KEY_CURRENCY);
    }

    public void setDepthDisplayMessages(int depthDisplayMessages) {
        editPrefs(depthDisplayMessages, KEY_DEPTH_DISPLAY_MESSAGE);
    }

    public void setNewPostMelody(String ringtone) {
        editPrefs(ringtone, KEY_NEW_POST_MELODY);
    }

    public void setDeliveryPoint(double latitude, double longitude, String str) {
        editPrefs(String.valueOf(latitude), LATITUDE_DP);
        editPrefs(String.valueOf(longitude), LONGITUDE_DP);
        editPrefs(str, ADRESS_DP);
    }

    public String getLatDP() {
        return mSettings.getString(LATITUDE_DP, "");
    }

    public String getLonDP() {
        return mSettings.getString(LONGITUDE_DP, "");
    }

    public String getAdressDP() {
        return mSettings.getString(ADRESS_DP, "");
    }


    public void setSumCashBox(Float sum) {
        editPrefs(sum, SUM_IN_CASHBOX);
    }

    public Float getSumCashBox() {
       return mSettings.getFloat(SUM_IN_CASHBOX, 0);
    }

    public String getUserId(){
        return mSettings.getString("KEY_USER_ID", "");
    }

}

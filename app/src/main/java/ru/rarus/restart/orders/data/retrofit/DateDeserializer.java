package ru.rarus.restart.orders.data.retrofit;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateDeserializer implements JsonDeserializer<Date> {

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = json.getAsString();
        Date returnDate = null;

        if (!TextUtils.isEmpty(date)) {

            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_FORMAT);
                returnDate = sdf.parse(date);
            } catch (ParseException e) {
                Log.e("DateDeserializer", "Data parser Exception: " + e.getMessage() + " " + e.getCause());
                returnDate = null;
            }
        }
        return returnDate;
    }
}

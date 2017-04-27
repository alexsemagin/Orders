package ru.rarus.restart.orders.di.managers;


import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.CallLogOperation;


public class CallLogManager extends BaseManager {

    private final static int NUMBER_OF_RECORDS = 20;

    public CallLogManager(Context context, DataBaseHelper dataBaseHelper, RxBus mRxBus) {
        super(context, dataBaseHelper, mRxBus);
    }

    public List<CallLogOperation> requestCallLog() {
        List<CallLogOperation> callList = new ArrayList<>();
        String[] projection = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.DATE,
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE};
        Cursor cursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if (callList.size() < NUMBER_OF_RECORDS) {
                        CallLogOperation operation = new CallLogOperation();
                        operation.setId(cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID)));
                        operation.setNumber(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                        operation.setName(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                        operation.setType(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
                        operation.setDate(new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)))));
                        callList.add(operation);
                    } else {
                        break;
                    }

                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        Collections.reverse(callList);
        return callList;
    }

}

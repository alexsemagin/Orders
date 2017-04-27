package ru.rarus.restart.orders.data.fcm;

import com.google.firebase.iid.FirebaseInstanceIdService;



/**
 * Created by kosigo on 07.02.17.
 */

public class MyFIIDS extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}

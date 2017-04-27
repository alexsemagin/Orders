package ru.rarus.restart.orders.ui.order.add.callLog;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;

public class CallLogActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CallLogFragment.class.getName());
        if (fragment == null) fragment = new CallLogFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, CallLogFragment.class.getName())
                .commit();
    }
}

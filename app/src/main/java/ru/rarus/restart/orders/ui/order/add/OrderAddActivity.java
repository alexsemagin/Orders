package ru.rarus.restart.orders.ui.order.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;


public class OrderAddActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Fragment fragment =getSupportFragmentManager().findFragmentByTag(OrderAddFragment.class.getName());
        if (fragment==null) fragment = new OrderAddFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment,OrderAddFragment.class.getName())
                .commit();
    }
}

package ru.rarus.restart.orders.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;


public class OrderActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        openFragment(OrderFragment.class, getIntent().getExtras());
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
}

package ru.rarus.restart.orders.ui.about_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_secondary);

        Fragment fragment = this.getSupportFragmentManager().findFragmentByTag(AboutFragment.class.getName());
        if (fragment == null) fragment = new AboutFragment();
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, AboutFragment.class.getName())
                .commit();
    }
}

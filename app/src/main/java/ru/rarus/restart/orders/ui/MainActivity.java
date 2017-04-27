package ru.rarus.restart.orders.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.component.UserComponent;
import ru.rarus.restart.orders.di.managers.DrawerManager;
import ru.rarus.restart.orders.di.managers.GEOManager;
import ru.rarus.restart.orders.di.managers.OrdersManager;
import ru.rarus.restart.orders.di.managers.UserManager;
import ru.rarus.restart.orders.ui.about_app.AboutActivity;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;
import ru.rarus.restart.orders.ui.account.AccountFragment;
import ru.rarus.restart.orders.ui.auth.AuthActivity;
import ru.rarus.restart.orders.ui.history.HistoryFragment;
import ru.rarus.restart.orders.ui.orders.list.OrderListFragment;
import ru.rarus.restart.orders.ui.orders.map.OrdersMapFragment;
import ru.rarus.restart.orders.ui.settings.SettingsActivity;


public class MainActivity extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    public final static int REQUEST_FINE_LOCATION = 777;
    public final static String IS_FIRST = "IS_FIRST";

    @Inject
    UserManager mUserManager; 

    @Inject
    OrdersManager mOrdersManager;

    @Inject
    GEOManager mGeoManager;

    @Inject
    DrawerManager mDrawerManager;

    @Inject
    RestartSettings mRestartSettings;

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.container)
    FrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        UserComponent userComponent = MyApp.getApp().getUserComponent();
        if (userComponent == null) {
            startAuthActivity();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return;
        }
        MyApp.getApp().getUserComponent().inject(this);

        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean(IS_FIRST)){
            mUserManager.singIn();
            getIntent().putExtra(IS_FIRST, false);
//            Observable.just(true)
//            .delay(4000, TimeUnit.MILLISECONDS)
//            .flatMap(r -> mOrdersManager.getCloseOrdersFromServer())
//            .subscribeOn(Schedulers.io()).subscribe(r->{}, e->{});
        }




        setUpDrawer(savedInstanceState);
        getDrawer().setToolbar(this, mainToolbar);
        getDrawer().setOnDrawerItemClickListener(this);
        ((TextView) getDrawer().getHeader().findViewById(R.id.name_account)).setText(mUserManager.getUser().getName());
        mDrawerManager.setDrawer(getDrawer());

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION},
//                    REQUEST_FINE_LOCATION);
//        }

        if (savedInstanceState == null) openFragment(OrderListFragment.class, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mGeoManager.requestLocationUpdates();
                } else {
                    if (getCurrentFocus() != null)
                        Snackbar.make(getCurrentFocus(), R.string.text_calculate_geo_close, Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        if (drawerItem != null) {
            if (drawerItem.getIdentifier() == DRAWER_ITEM_ORDERS) {
                openFragment(OrderListFragment.class, null);
                return false;
            }
            if (drawerItem.getIdentifier() == DRAWER_ITEM_ACCOUNT) {
                openFragment(AccountFragment.class, null);
                return false;
            }
            if (drawerItem.getIdentifier() == DRAWER_ITEM_HISTORY) {
                openFragment(HistoryFragment.class, null);
                return false;
            }
            if (drawerItem.getIdentifier() == DRAWER_ITEM_CALL_OPERATOR) {
                showCall();
                return false;
            }
            if (drawerItem.getIdentifier() == DRAWER_ITEM_SETTINGS) {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
            if (drawerItem.getIdentifier() == DRAWER_ITEM_ABOUT_APP) {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return false;
            }
            if (drawerItem.getIdentifier() == DRAWER_ITEM_EXIT) {
                mUserManager.singOut();
                MyApp.getApp().removeUserComponent();
                startAuthActivity();
                return false;
            }
        }
        return false;
    }

    private void startAuthActivity() {

        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof OrdersMapFragment) {
            detachFragment(OrdersMapFragment.class);
        } else {
            super.onBackPressed();
        }
    }

    private void showCall() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        String num = new RestartSettings(this)
                .getOperatorPhoneNumber();
        String numWithBrackets = mRestartSettings.getOperatorPhoneNumberWithBrackets();
        String uri = "tel:" + num;
        MaterialDialog dropDialog = builder
                .title(R.string.text_operator_call_title)
                .content(numWithBrackets)
                .negativeText(R.string.text_operator_call_negative)
                .positiveText(R.string.text_operator_call_positive)
                .onPositive((dialog, which) -> {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    } catch (Exception e) {
                        Intent callIntent = new Intent(Intent.ACTION_VIEW);
                        callIntent.setData(Uri.parse(uri));
                        startActivity(callIntent);
                    }
                })
                .onNegative((dialog, which) -> dialog.cancel())
                .build();
        dropDialog.show();
    }
}

package ru.rarus.restart.orders.ui.abscracts;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import ru.rarus.restart.orders.R;


public class BaseActivity extends AppCompatActivity {

    private Drawer drawer = null;
    public final static int DRAWER_ITEM_ORDERS = 1;
    public final static int DRAWER_ITEM_ACCOUNT = 2;
    public final static int DRAWER_ITEM_HISTORY = 3;
    public final static int DRAWER_ITEM_CALL_OPERATOR = 4;
    public final static int DRAWER_ITEM_SETTINGS = 5;
    public final static int DRAWER_ITEM_ABOUT_APP = 6;
    public final static int DRAWER_ITEM_EXIT = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (isPhone()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected void setUpDrawer(@Nullable Bundle savedInstanceState) {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.drawer_header_main)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_orders).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(R.drawable.vector_orders).withSelectedIcon(R.drawable.vector_orders_selected).withIdentifier(DRAWER_ITEM_ORDERS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_current).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(R.drawable.vector_account).withSelectedIcon(R.drawable.vector_account_selected).withIdentifier(DRAWER_ITEM_ACCOUNT),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_history).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(ContextCompat.getDrawable(this,R.drawable.vector_history)).withSelectedIcon(ContextCompat.getDrawable(this,R.drawable.vector_history_selected)).withIdentifier(DRAWER_ITEM_HISTORY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_call).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(R.drawable.vector_call_phone).withSelectedIcon(R.drawable.vector_call_phone_selected).withIdentifier(DRAWER_ITEM_CALL_OPERATOR).withSelectable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(R.drawable.vector_settings).withSelectedIcon(R.drawable.vector_settings_selected).withIdentifier(DRAWER_ITEM_SETTINGS).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_about_app).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(R.drawable.vector_about_app).withSelectedIcon(R.drawable.vector_about_app_selected).withIdentifier(DRAWER_ITEM_ABOUT_APP).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_sign_out).withTextColor(ContextCompat.getColor(this,R.color.md_grey_600)).withIcon(R.drawable.vector_sing_out).withSelectedIcon(R.drawable.vector_sing_out_selected).withIdentifier(DRAWER_ITEM_EXIT)
                )

                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {

                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (getCurrentFocus() != null)
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .build();
  }

    protected boolean isPhone() {
        boolean isPhone = (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        return !isPhone;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (drawer != null) outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    protected Drawer getDrawer() {
        return drawer;
    }

    public void openFragment(Class fragmentClass, Bundle bundle) {
        Fragment fragmentID = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragmentID != null) {
            getSupportFragmentManager().beginTransaction()
                    .detach(fragmentID)
                    .commit();
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                if (bundle != null && fragment != null) fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment, fragmentClass.getName())
                        .commit();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }


    public void addFragment(Class fragmentClass, Bundle bundle) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                if (bundle != null && fragment != null) fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, fragment, fragmentClass.getName())
                        .commit();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    public void removeFragment(Class fragmentClass) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public void detachFragment(Class fragmentClass) {
        Fragment fragmentID = getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
        if (fragmentID != null) {
            getSupportFragmentManager().beginTransaction()
                    .detach(fragmentID)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}

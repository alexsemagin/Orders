package ru.rarus.restart.orders.ui.auth;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;


import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;


public class AuthActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setupWindowAnimations();
      // setupWindowAnimationsSlide();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(AuthFragment.class.getName());
        if(fragment == null) fragment = new AuthFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, AuthFragment.class.getName())
                .commit();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimationsSlide() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(500);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }


    private void setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(400);
            getWindow().setReenterTransition(fade);
            getWindow().setExitTransition(fade);
        }

    }




}

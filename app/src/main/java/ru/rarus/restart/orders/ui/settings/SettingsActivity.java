package ru.rarus.restart.orders.ui.settings;


import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;

public class SettingsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_secondary);
      //  setupWindowAnimations();
        setupWindowAnimationsSlide();

        Fragment fragment = this.getSupportFragmentManager().findFragmentByTag(SettingsFragment.class.getName());
        if (fragment == null) fragment = new SettingsFragment();
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, SettingsFragment.class.getName())
                .commit();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimationsSlide() {
        // Re-enter transition is executed when returning to this activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.RIGHT);
            slideTransition.setDuration(400);

            Slide slideTransitionBottom = new Slide();
            slideTransitionBottom.setSlideEdge(Gravity.BOTTOM);
            slideTransitionBottom.setDuration(200);

            getWindow().setEnterTransition(slideTransition);
            //getWindow().setReenterTransition(slideTransition);
            //getWindow().setExitTransition(slideTransitionBottom);
            getWindow().setReturnTransition(slideTransitionBottom);
        }
    }


}

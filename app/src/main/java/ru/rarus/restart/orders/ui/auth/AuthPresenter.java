package ru.rarus.restart.orders.ui.auth;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.fabric.sdk.android.services.network.HttpRequest;
import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.managers.SyncManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import rx.schedulers.Schedulers;


public class AuthPresenter extends BasePresenter<AuthView> {

    @Inject
    SyncManager syncManager;

    @Inject
    RestartSettings restartSettings;

    private Boolean processingDemo = false;

    private String mLogin = "";

    AuthPresenter() {
        MyApp.getApp().getComponent().inject(this);
    }

    void onSettingsClick() {
        if (mView != null) mView.startSettingsFragment();
    }

    void signIn() {

        if (!processingDemo) {
            processingDemo = true;

            if (mView != null) mView.setProgress(true);
            syncManager.signIn(mLogin)

                    .subscribe(r -> {
                                if (mView != null) {
                                    mView.setProgress(false);
                                    if (r) mView.startMainActivity();
                                }
                            }
                            , e -> {
                                processingDemo=false;
                                mView.setProgress(false);
                                cancelClicked();
                                mView.showMessage(e.getMessage());
                            }
                    );
        }
    }




    public void cancelClicked() {
        mLogin = "";
        mView.showInputPass("");
    }


    public void showLoginPass() {
        String loginView = "";
        for (int i = 0; i < mLogin.length(); i++) loginView += "•";
        mView.showInputPass(loginView);
    }


    public void numberClicked(String numberClicked) {
        mLogin = mLogin + numberClicked;
        showLoginPass();
    }

    public void signInDemo() {

        syncManager.createDemo();
        if (mView!=null) mView.startMainActivity();

//        if (!processingDemo) {
//            processingDemo=true;
//            mLogin = InfoStorage.DEMO_PASSWD;
//            showLoginPass();
//
//            restartSettings.setAlternativeServer(false);
//            if (mView != null) mView.setProgress(true);
//            syncManager.signIn(mLogin)
//                    .subscribe(r -> {
//
//                                if (mView != null) {
//                                    mView.setProgress(false);
//                                    if (r) mView.startMainActivity();
//                                }
//                            },
//                            e -> {
//                                signInDemoAlternativeServer();
//                            });
//        }
    }

    private void signInDemoAlternativeServer() {

    }
}


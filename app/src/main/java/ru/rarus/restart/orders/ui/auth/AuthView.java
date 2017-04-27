package ru.rarus.restart.orders.ui.auth;

import ru.rarus.restart.orders.ui.abscracts.BasePresenter;


public interface AuthView extends BasePresenter.BaseView {

        void startMainActivity();
        void startSettingsFragment();
        void showInputPass(String mLogin);

}

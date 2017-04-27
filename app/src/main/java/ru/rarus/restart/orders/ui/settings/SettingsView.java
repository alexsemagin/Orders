package ru.rarus.restart.orders.ui.settings;

import android.content.Context;

import com.google.android.gms.maps.model.LatLngBounds;

import ru.rarus.restart.orders.ui.abscracts.BasePresenter;

interface SettingsView extends BasePresenter.BaseView {

    Context getFragmentContext();

    void initServerAddressPref(String defaultValue, int title, boolean disabled);

    void initAlternativeServerPref(boolean defaultValue, int title);

    void initAlternativeServerAddressPref(String defaultValue, int title, boolean clickable);


    void initOrganizationNamePref(String defaultValue, int title);

    void initCurrencyPref(String defaultValue, int title);


    void initNewPostMelody(String defaultValue, int title);

    void openPointDelivery(LatLngBounds latLngBounds);

    void initDeliveryPoint(String str);
}

package ru.rarus.restart.orders.ui.settings;

import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.data.settings.SettingsValidator;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;


public class SettingsPresenter extends BasePresenter<SettingsView> {

    @Inject
    RestartSettings restartSettings;
    private boolean isOpen = false;


    SettingsPresenter() {
        MyApp.getApp().getComponent().inject(this);

    }

    void initSettingsLogic() {
        initPreferencesView();
    }

    private void initPreferencesView() {
        mView.initServerAddressPref(restartSettings.getServerAddress(), R.string.pref_server_address_name, restartSettings.isAlternativeServer());
        mView.initAlternativeServerPref(restartSettings.isAlternativeServer(), R.string.pref_alternative_server_name);
        mView.initAlternativeServerAddressPref(restartSettings.getAlternativeServerAddress(),
                R.string.pref_alternative_server_address_name, restartSettings.isAlternativeServer());

        mView.initOrganizationNamePref(restartSettings.getOrganizationName(), R.string.pref_organization_name);
        mView.initCurrencyPref(restartSettings.getCurrency(), R.string.pref_currency_name);

        mView.initNewPostMelody(getRingtoneUri(), R.string.pref_melody_name);
        mView.initDeliveryPoint(restartSettings.getAdressDP());
    }

    String getRingtoneUri() {
        Uri uri = Uri.parse(restartSettings.getNewPostMelody());
        return getRingtoneName(uri);
    }

    Uri getUri() {
        return Uri.parse(restartSettings.getNewPostMelody());
    }

    private void createTextDialogFragment(String value, int prefName, PreferencesCallBack callBack) {

        new MaterialDialog.Builder(mView.getFragmentContext())
                .title(prefName)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, value, (dialog, input) -> {
                            if ((prefName == R.string.pref_server_address_name || prefName == R.string.pref_alternative_server_address_name)
                                    && !SettingsValidator.isValidUrl(String.valueOf(input))) {
                                callBack.onResult(String.valueOf(value));
                                mView.showMessage(mView.getFragmentContext().getResources().getString(R.string.error_snack_message_incorrect_server_url));
                            } else if (prefName == R.string.pref_currency_name) {
                                if (SettingsValidator.isValidCurrency(String.valueOf(input))) {
                                    callBack.onResult(String.valueOf(input).toLowerCase());
                                } else {
                                    callBack.onResult(String.valueOf(value));
                                    mView.showMessage(mView.getFragmentContext().getResources().getString(R.string.error_snack_message_incorrect_currency));
                                }
                            } else {
                                callBack.onResult(String.valueOf(input));
                            }
                        }
                )
                .positiveText(mView.getFragmentContext().getResources().getString(R.string.dialog_positive_button_title))
                .negativeText(mView.getFragmentContext().getResources().getString(R.string.dialog_negative_button_title))
                .show();
    }


    void onServerAddressClicked(int title) {
        createTextDialogFragment(restartSettings.getServerAddress(), title, this::onServerAddressChanged);
    }

    void onAlternativeServerClicked() {
        restartSettings.setAlternativeServer(!restartSettings.isAlternativeServer());
        mView.initAlternativeServerPref(restartSettings.isAlternativeServer(), R.string.pref_alternative_server_name);
        mView.initServerAddressPref(restartSettings.getServerAddress(), R.string.pref_server_address_name, restartSettings.isAlternativeServer());
        mView.initAlternativeServerAddressPref(restartSettings.getAlternativeServerAddress(),
                R.string.pref_alternative_server_address_name, restartSettings.isAlternativeServer());
    }

    void onAlternativeServerAddressClicked(int title) {
        createTextDialogFragment(restartSettings.getAlternativeServerAddress(), title,
                this::onAlternativeServerAddressChanged);
    }

    void onOrganizationNameClicked(int title) {
        createTextDialogFragment(restartSettings.getOrganizationName(), title,
                this::onOrganizationNameChanged);
    }

    void onCurrencyClicked(int title) {
        createTextDialogFragment(restartSettings.getCurrency(), title, this::onCurrencyChanged);
    }

    /**
     * CallBacks for all prefs
     */
    private void onServerAddressChanged(String s) {
        restartSettings.setServerAddress(s);
        mView.initServerAddressPref(s, R.string.pref_server_address_name, restartSettings.isAlternativeServer());
    }

    private void onAlternativeServerAddressChanged(String s) {
        restartSettings.setAlternativeServerAddress(s);
        mView.initAlternativeServerAddressPref(restartSettings.getAlternativeServerAddress(),
                R.string.pref_alternative_server_address_name, restartSettings.isAlternativeServer());
    }

    private void onOrganizationNameChanged(String s) {
        restartSettings.setOrganizationName(s);
        mView.initOrganizationNamePref(restartSettings.getOrganizationName(),
                R.string.pref_organization_name);
    }

    private void onCurrencyChanged(String s) {
        restartSettings.setCurrency(s);
        mView.initCurrencyPref(restartSettings.getCurrency(), R.string.pref_currency_name);
    }

    void onRingtoneChanged(Uri uri) {
        if (uri != null) {
            restartSettings.setNewPostMelody(uri.toString());
            mView.initNewPostMelody(getRingtoneName(uri), R.string.pref_melody_name);
        } else {
            restartSettings.setNewPostMelody(getView().getFragmentContext().getResources().getString(R.string.no_ringtone));
            mView.initNewPostMelody(getView().getFragmentContext().getResources().getString(R.string.no_ringtone), R.string.pref_melody_name);
        }
    }

    private String getRingtoneName(Uri uri) {
        Cursor cursor = mView.getFragmentContext().getApplicationContext()
                .getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String result = cursor.getString(nameIndex);
            cursor.close();
            return result;
        } else {
            return getView().getFragmentContext().getResources().getString(R.string.no_ringtone);
        }
    }

    void clickAddressDelivery() {
        if (!isOpen) {
            isOpen = true;
            mView.openPointDelivery(getLatLngBounds());
        }
    }

    void setDeliveryPoint(double latitude, double longitude, String strAddress) {
        restartSettings.setDeliveryPoint(latitude, longitude, strAddress);
        if (mView != null) mView.initDeliveryPoint(strAddress);
    }

    private LatLngBounds getLatLngBounds() {
        try {
            LatLng southwest = new LatLng(Double.valueOf(restartSettings.getLatDP()) - 0.2, Double.valueOf(restartSettings.getLonDP()) - 0.2);
            LatLng northeast = new LatLng(Double.valueOf(restartSettings.getLatDP()) + 0.2, Double.valueOf(restartSettings.getLonDP()) + 0.2);
            return new LatLngBounds(southwest, northeast);
        } catch (Exception e) {
            return null;
        }
    }


    void setIsOpen() {
        isOpen = false;
    }
}

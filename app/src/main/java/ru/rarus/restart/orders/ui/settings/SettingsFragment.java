package ru.rarus.restart.orders.ui.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.abscracts.UiUtils;

public class SettingsFragment extends BaseFragment implements SettingsView {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 111;
    public static final int ACTIVITY_CHOOSE_FILE = 777;
    private static final int REQUEST_PLACE_PICKER = 888;

    private SettingsPresenter presenter;
    private View mView;

    @BindView(R.id.ll_conn_settings_head)
    LinearLayout llConnSettingsHead;
    @BindView(R.id.tv_conn_settings_head)
    TextView tvConnSettingsHead;

    @BindView(R.id.ll_serv_address)
    LinearLayout llServAddr;
    @BindView(R.id.tv_serv_addr_prim_txt)
    TextView tvServAddrPrimTxt;
    @BindView(R.id.tv_serv_addr_sec_txt)
    TextView tvServAddrSecTxt;

    @BindView(R.id.rl_alt_server)
    RelativeLayout rlAltServ;
    @BindView(R.id.tv_alt_serv_prim_txt)
    TextView tvAltServPrimTxt;
    @BindView(R.id.swch_alt_serv)
    SwitchCompat switchCompat;

    @BindView(R.id.ll_alt_address)
    LinearLayout llAltAddress;
    @BindView(R.id.tv_alt_address_prim_txt)
    TextView tvAltAddressPrimTxt;
    @BindView(R.id.tv_alt_address_sec_txt)
    TextView tvAltAddressSecTxt;

    @BindView(R.id.ll_display_settings_head)
    LinearLayout llDisplaySettingsHead;
    @BindView(R.id.tv_display_settings_head)
    TextView tvDisplaySettingsHead;

    @BindView(R.id.ll_organization)
    LinearLayout llOrgName;
    @BindView(R.id.tv_organization_prim_txt)
    TextView tvOrgNamePrimTxt;
    @BindView(R.id.tv_organization_sec_txt)
    TextView tvOrgNameSecTxt;

    @BindView(R.id.ll_currency)
    LinearLayout llCurrency;
    @BindView(R.id.tv_currency_prim_txt)
    TextView tvCurrencyPrimTxt;
    @BindView(R.id.tv_currency_sec_txt)
    TextView tvCurrencySecTxt;

    @BindView(R.id.ll_alert_settings_head)
    LinearLayout llAlertSettingsHead;
    @BindView(R.id.tv_alert_settings_head)
    TextView tvAlertSettingsHead;

    @BindView(R.id.ll_post_melody)
    LinearLayout llPostMelody;
    @BindView(R.id.tv_post_melody_prim_txt)
    TextView tvPostMelodyPrimTxt;
    @BindView(R.id.tv_post_melody_sec_txt)
    TextView tvPostMelodySecTxt;


    @BindView(R.id.tv_point_deliver)
    TextView tvPointDeliver;
    @BindView(R.id.ll_location_click)
    LinearLayout llLocationClick;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new SettingsPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar;
        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.title_settings);
        toolbar.getMenu().clear();
        if (getActivity() instanceof SettingsActivity) {
            toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
            toolbar.setNavigationOnClickListener(mView -> getActivity().onBackPressed());
        }

        llLocationClick.setOnClickListener(v -> presenter.clickAddressDelivery());

        presenter.setView(this);
        presenter.initSettingsLogic();
    }

    @Override
    public Context getFragmentContext() {
        return this.getContext();
    }

    @Override
    public void initServerAddressPref(String def, int title, boolean disabled) {
        if (disabled) {
            llServAddr.setOnClickListener(null);
            setTextStyle(R.style.TextAppearance_Medium_Disabled, R.style.TextAppearance_Small_Disabled);
        } else {
            llServAddr.setOnClickListener(v -> presenter.onServerAddressClicked(title));
            setTextStyle(R.style.TextAppearance_Medium, R.style.TextAppearance_Small);
        }
        llServAddr.setClickable(!disabled);
        tvServAddrPrimTxt.setText(title);
        tvServAddrSecTxt.setText(def);
    }

    private void setTextStyle(int primatyId, int secondaryId) {
        if (Build.VERSION.SDK_INT < 23) {
            tvServAddrPrimTxt.setTextAppearance(getContext(), primatyId);
            tvServAddrSecTxt.setTextAppearance(getContext(), secondaryId);
        } else {
            tvServAddrPrimTxt.setTextAppearance(primatyId);
            tvServAddrSecTxt.setTextAppearance(secondaryId);
        }
    }

    @Override
    public void initAlternativeServerPref(boolean defaultValue, int title) {
        rlAltServ.setOnClickListener(v -> presenter.onAlternativeServerClicked());
        tvAltServPrimTxt.setText(title);
        if (defaultValue) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
    }

    @Override
    public void initAlternativeServerAddressPref(String defaultValue, int title, boolean visible) {
        tvAltAddressPrimTxt.setText(title);
        tvAltAddressSecTxt.setText(defaultValue);
        llAltAddress.setOnClickListener(v -> presenter.onAlternativeServerAddressClicked(title));
        if (visible) {
            UiUtils.expand(llAltAddress);
        } else {
            UiUtils.collapse(llAltAddress);
        }
    }

    @Override
    public void initOrganizationNamePref(String defaultValue, int title) {
        llOrgName.setOnClickListener(v -> presenter.onOrganizationNameClicked(title));
        tvOrgNamePrimTxt.setText(title);
        tvOrgNameSecTxt.setText(defaultValue);
    }

    @Override
    public void initCurrencyPref(String defaultValue, int title) {
        llCurrency.setOnClickListener(v -> presenter.onCurrencyClicked(title));
        tvCurrencyPrimTxt.setText(title);
        tvCurrencySecTxt.setText(defaultValue);
    }

    @Override
    public void initNewPostMelody(String defaultValue, int title) {
        llPostMelody.setOnClickListener(v ->
                checkReadStroragePermission()
        );
        tvPostMelodyPrimTxt.setText(title);
        tvPostMelodySecTxt.setText(defaultValue);
    }

    @Override
    public void openPointDelivery(LatLngBounds latLngBounds) {

        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            if (latLngBounds!=null) intentBuilder.setLatLngBounds(latLngBounds);
            Intent intent = intentBuilder.build(getActivity());

            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initDeliveryPoint(String str) {
        tvPointDeliver.setText(str.isEmpty()?getResources().getString(R.string.text_not_set):str);
    }

    public void checkReadStroragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(mView, getResources().getString(R.string.read_ext_storage_explanation), Snackbar.LENGTH_SHORT).show();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            startRingtonePickerIntent();
        }
    }

    public void startRingtonePickerIntent() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getResources().getString(R.string.select_ringtone));
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);

        if (presenter.getRingtoneUri().equals(getResources().getString(R.string.no_ringtone))) {
            RingtoneManager ringtoneManager = new RingtoneManager(getActivity());
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtoneManager.getRingtoneUri(0));
        } else {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, presenter.getUri());
        }
        this.startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        presenter.setIsOpen();
        if (resultCode == Activity.RESULT_OK && requestCode == ACTIVITY_CHOOSE_FILE) {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                presenter.onRingtoneChanged(uri);
            } else {
                presenter.onRingtoneChanged(null);
            }
        }  else if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(getActivity(),intent);
            String str = place.getName()==null?"":place.getName() +(place.getAddress()==null?"": "\n"+place.getAddress());
            presenter.setDeliveryPoint(place.getLatLng().latitude, place.getLatLng().longitude, str);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case SettingsFragment.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED
                        && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(mView, getResources().getString(R.string.permission_canceled), Snackbar.LENGTH_SHORT).show();
                } else {
                    startRingtonePickerIntent();
                }
            }
        }
    }



}

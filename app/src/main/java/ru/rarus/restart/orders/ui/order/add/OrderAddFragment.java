package ru.rarus.restart.orders.ui.order.add;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.order.add.callLog.CallLogActivity;
import ru.rarus.restart.orders.ui.order.add.callLog.CallLogFragment;

public class OrderAddFragment extends BaseFragment implements OrderAddInterface, SwitchDateTimeDialogFragment.OnButtonClickListener {

    private static final int REQUEST_PLACE_PICKER = 888;
    private static final int REQUEST_PICK_FROM_CONTACT = 777;
    private static final int REQUEST_PICK_FROM_HYSTORY = 666;
    private static final String HAS_PHONE = "1";
    private static final String EQUALS = " = ";
    private static final String INDEX_DATA1 = "data1";


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.address_ll)
    LinearLayout addressLl;
    @BindView(R.id.tv_point_deliver)
    TextView tvPointDeliver;
    @BindView(R.id.marker_iv)
    ImageView markerIv;
    @BindView(R.id.date_iv)
    ImageView dateIv;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.date_ll)
    LinearLayout dateLl;
    @BindView(R.id.name_iv)
    ImageView nameIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.name_ll)
    LinearLayout nameLl;
    @BindView(R.id.call_iv)
    ImageView callIv;
    @BindView(R.id.tv_call_deliver)
    TextView tvCallDeliver;
    @BindView(R.id.call_ll)
    LinearLayout callLl;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    MaterialDialog dialog;
    EditText editText;
    private OrderAddPresenter mPresenter;
    private SwitchDateTimeDialogFragment dateTimeFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new OrderAddPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_add, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.text_new_order);
        toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        nameLl.setOnClickListener(v -> mPresenter.clickName());
        addressLl.setOnClickListener(v -> mPresenter.clickAddress());
        dateLl.setOnClickListener(v -> mPresenter.clickDate());
        callLl.setOnClickListener(v -> mPresenter.clickCall());
        fab.setOnClickListener(v -> mPresenter.clickFab());
        dateTimeFragment = (SwitchDateTimeDialogFragment) getFragmentManager().findFragmentByTag(SwitchDateTimeDialogFragment.class.getName());
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.text_date_time),
                    getString(R.string.positive_button_datetime_picker),
                    getString(R.string.text_cansel)
            );
        }
        dateTimeFragment.setOnButtonClickListener(this);


        mPresenter.setView(this);
        mPresenter.getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.setView(null);
        unbinder.unbind();
    }

    @Override
    public void chooseAddress(LatLngBounds latLngBounds) {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            if (latLngBounds != null) intentBuilder.setLatLngBounds(latLngBounds);
            Intent intent = intentBuilder.build(getActivity());
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showSelectDate() {
        dateTimeFragment.show(getFragmentManager(), SwitchDateTimeDialogFragment.class.getName());
    }

    @Override
    public void showSetName(String name) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_set_name)
                .negativeText(R.string.text_cansel)
                .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText(R.string.text_ok)
                .input(null, name, false, (dialog, input) -> mPresenter.setName(input))
                .show();
    }

    @Override
    public void showSetNumberCall(Editable number) {
        dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.text_set_number_phone)
                .inputType(InputType.TYPE_CLASS_PHONE)
                .customView(R.layout.custom_dialog_view, true)
                .show();
        TextView tvAddContacts = (TextView) dialog.getCustomView().findViewById(R.id.text_view_add_contacts);
        TextView tvAddCallLog = (TextView) dialog.getCustomView().findViewById(R.id.text_view_add_calllog);
        TextView tvAddOk = (TextView) dialog.getCustomView().findViewById(R.id.text_view_add_ok);

        editText = (EditText) dialog.getCustomView().findViewById(R.id.edit_text_input_phone);
        editText.setText(number);
        if (String.valueOf(editText.getText()).isEmpty()) {
            tvAddOk.setEnabled(false);
            tvAddOk.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_blue_200));
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(editText.getText()).matches(getString(R.string.regular_expression))) {
                    tvAddOk.setEnabled(true);
                    tvAddOk.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));
                } else {
                    tvAddOk.setEnabled(false);
                    tvAddOk.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_blue_200));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvAddCallLog.setOnClickListener(v -> mPresenter.clickCallLog());
        tvAddContacts.setOnClickListener(v -> mPresenter.clickContacts());
        tvAddOk.setOnClickListener(v -> {
            mPresenter.setNumber(new SpannableStringBuilder(editText.getText()));
            dialog.dismiss();
        });
    }

    @Override
    public void checkReadContactsPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, REQUEST_PICK_FROM_CONTACT);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                if (getActivity().getCurrentFocus() != null)
                    Snackbar.make(getActivity().getCurrentFocus(), R.string.permission_contact_rationale, Snackbar.LENGTH_LONG).show();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_PICK_FROM_CONTACT);
            }
        }
    }

    @Override
    public void initSelectCallLogIntent() {
        Intent intent = new Intent(getActivity().getApplicationContext(), CallLogActivity.class);
        startActivityForResult(intent, REQUEST_PICK_FROM_HYSTORY);
    }

    @Override
    public void finishActivity() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PICK_FROM_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_FROM_CONTACT);
            } else {
                if (getActivity().getCurrentFocus() != null)
                    Snackbar.make(getActivity().getCurrentFocus(), R.string.text_contact_cancelled, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void showName(String name) {
        nameTv.setText(name);
    }

    @Override
    public void showFab(boolean b) {
        if (b) fab.show();
        else fab.hide();
    }

    @Override
    public void showDate(Date date) {
        dateTv.setText(InfoStorage.formatDate.format(date));
    }

    @Override
    public void showAddress(String address) {
        tvPointDeliver.setText(address);
    }

    @Override
    public void showNumber(Editable number) {
        tvCallDeliver.setText(number);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == REQUEST_PLACE_PICKER && resultCode == Activity.RESULT_OK) {
            final Place place = PlacePicker.getPlace(getActivity(), intent);
            String str = place.getName() == null ? "" : place.getName() + (place.getAddress() == null ? "" : "\n" + place.getAddress());
            mPresenter.setDeliveryPoint(place.getLatLng().latitude, place.getLatLng().longitude, str);

        } else if (requestCode == REQUEST_PICK_FROM_CONTACT && resultCode == Activity.RESULT_OK) {
            Uri contactData = intent.getData();
            Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase(HAS_PHONE)) {
                    Cursor phones = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + EQUALS + id,
                            null, null);
                    if (phones != null) {
                        phones.moveToFirst();
                    }
                    editText.setText(new SpannableStringBuilder(phones.getString(phones.getColumnIndex(INDEX_DATA1))));
                    phones.close();
                }
            }
        } else if (requestCode == REQUEST_PICK_FROM_HYSTORY && resultCode == Activity.RESULT_OK) {
            editText.setText(new SpannableStringBuilder(intent.getStringExtra(CallLogFragment.INTENT_PHONE)));
        }
    }

    @Override
    public void onPositiveButtonClick(Date date) {
        mPresenter.setDate(date);
    }

    @Override
    public void onNegativeButtonClick(Date date) {
    }
}

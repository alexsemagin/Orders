package ru.rarus.restart.orders.ui.order;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Cancel;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.entities.OrderRow;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.abscracts.UiUtils;

import static ru.rarus.restart.orders.data.info.InfoStorage.dfMoney;
import static ru.rarus.restart.orders.data.info.InfoStorage.formatDate;
import static ru.rarus.restart.orders.ui.order.OrderPresenter.ATTACH_ORDER;
import static ru.rarus.restart.orders.ui.order.OrderPresenter.CANCEL_ORDER;
import static ru.rarus.restart.orders.ui.order.OrderPresenter.COMPLETE_ORDER;
import static ru.rarus.restart.orders.ui.order.OrderPresenter.DETACH_ORDER;


public class OrderFragment extends BaseFragment implements OrderView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_tv_time_route)
    TextView orderTvTimeRoute;
    @BindView(R.id.order_tv_name_customer)
    TextView orderTvNameCustomer;
    @BindView(R.id.order_tv_number_order)
    TextView orderTvNumberOrder;
    @BindView(R.id.fab)
    com.getbase.floatingactionbutton.FloatingActionButton fab;
    @BindView(R.id.container_map)
    FrameLayout containerMap;


    @BindView(R.id.order_fl_button_1)
    FrameLayout orderFlButton1;
    @BindView(R.id.order_fl_button_2)
    FrameLayout orderFlButton2;
    @BindView(R.id.order_fl_button_3)
    FrameLayout orderFlButton3;

    @BindView(R.id.order_tv_sum)
    TextView orderTvSum;
    @BindView(R.id.order_tv_status)
    TextView orderTvStatus;
    @BindView(R.id.order_tv_short_change)
    TextView orderTvShortChange;


    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.order_address)
    TextView orderAddress;
    @BindView(R.id.order_comments)
    TextView orderComments;
    @BindView(R.id.order_item_name)
    TextView orderItemName;
    @BindView(R.id.order_call)
    TextView orderCall;
    @BindView(R.id.order_call_click)
    LinearLayout orderCallClick;


    @BindView(R.id.order_container_row)
    LinearLayout orderContainerRow;
    @BindView(R.id.order_discount)
    TextView orderDiscount;
    @BindView(R.id.order_discount_sum)
    TextView orderDiscountSum;
    @BindView(R.id.order_total_count)
    TextView orderTotalCount;
    @BindView(R.id.order_total_sum)
    TextView orderTotalSum;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.action_a)
    com.getbase.floatingactionbutton.FloatingActionButton actionA;
    @BindView(R.id.action_b)
    com.getbase.floatingactionbutton.FloatingActionButton actionB;
    @BindView(R.id.action_c)
    com.getbase.floatingactionbutton.FloatingActionButton actionC;
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu multipleActions;
    @BindView(R.id.fab_get_order)
    com.getbase.floatingactionbutton.FloatingActionButton fabGetOrder;


    private OrderPresenter mPresenter;
    private MaterialDialog pd;
    private MarkerOptions currentPositionMarker;
    private Marker currentLocationMarker;
    private GoogleMap mGoogleMap;
    private HashMap<String, String> hashCancel = new HashMap<>();
    private Bitmap icMarkerPlace;
    private Bitmap icFreeMarker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new OrderPresenter(getArguments().getString("ID"));
        icMarkerPlace = UiUtils.getBitmapFromVectorDrawable(getActivity(), R.drawable.vector_drawable_ic_place_current);
        icFreeMarker = UiUtils.getBitmapFromVectorDrawable(getActivity(), R.drawable.vector_drawable_ic_add_location);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        toolbar.setTitle("");
        toolbar.getMenu().clear();
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.vector_arrow_back));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        pd = new MaterialDialog.Builder(getActivity())
                .content(R.string.text_send_data_to_server)
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();


        mPresenter.setView(this);
        mPresenter.getData();
    }


    @Override
    public void showOrder(Order order) {

        orderFlButton1.removeAllViews();
        orderFlButton2.removeAllViews();
        orderFlButton3.removeAllViews();

        if (order.isMy()) {
            multipleActions.setVisibility(View.VISIBLE);
            multipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
                @Override
                public void onMenuExpanded() {
                    fab.setVisibility(View.GONE);
                }

                @Override
                public void onMenuCollapsed() {
                    fab.setVisibility(View.VISIBLE);
                }
            });
            actionA.setOnClickListener(v -> mPresenter.clickAction(CANCEL_ORDER));
            actionB.setOnClickListener(v -> showDialogDetach());
            actionC.setOnClickListener(v -> mPresenter.clickAction(COMPLETE_ORDER));

//            orderFlButton1.setOnClickListener(v -> mPresenter.clickAction(CANCEL_ORDER));
//            orderFlButton2.setOnClickListener(v -> showDialogDetach());
//            orderFlButton3.setOnClickListener(v -> mPresenter.clickAction(COMPLETE_ORDER));
//            if (getActivity() != null)
//                getActivity().getLayoutInflater().inflate(R.layout.placeholder_action_cancel, orderFlButton1, true);
//            if (getActivity() != null)
//                getActivity().getLayoutInflater().inflate(R.layout.placeholder_action_free, orderFlButton2, true);
//            if (getActivity() != null)
//                getActivity().getLayoutInflater().inflate(R.layout.placeholder_action_complete, orderFlButton3, true);

        } else {
            fab.setVisibility(View.GONE);
            fabGetOrder.setVisibility(View.VISIBLE);
            fabGetOrder.setOnClickListener(v -> showDialogAttach());
//            orderFlButton3.setOnClickListener(v -> showDialogAttach());
//            if (getActivity() != null)
//                getActivity().getLayoutInflater().inflate(R.layout.placeholder_action_accept_order, orderFlButton3, true);
        }

        orderTvTimeRoute.setText(order.getLocation()==null?"":order.getLocation().getStringLocation());
        orderTvNameCustomer.setText(order.getName());
        orderTvNumberOrder.setText(order.getNumString());

        orderTvSum.setText(InfoStorage.dfMoney.format(order.getSumOrder()));

        orderTvSum.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));
        orderTvStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));
        orderTvShortChange.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary));


        switch (order.getStatusPay()) {
            case Order.STATUS_PAY.NOT_PAY:
                orderTvStatus.setText(R.string.text_order_pay_ok);
                orderTvShortChange.setText("" + getString(R.string.text_money_back) + " " + InfoStorage.dfMoney.format(order.getShortChange()));

                break;
            case Order.STATUS_PAY.PAID:
                orderTvSum.setTextColor(ContextCompat.getColor(getActivity(), R.color.accent));
                orderTvStatus.setText(R.string.text_pay_complete);
                orderTvStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.accent));
                orderTvShortChange.setText("" + getString(R.string.text_money_backed) + " " + InfoStorage.dfMoney.format(order.getShortChange()));
                orderTvShortChange.setTextColor(ContextCompat.getColor(getActivity(), R.color.accent));

                break;
            default:
                orderTvStatus.setText(getResources().getString(R.string.text_no_info));
                orderTvShortChange.setText(InfoStorage.dfMoney.format(order.getDeliveryClientSum()));
                break;
        }

        orderTime.setText(InfoStorage.formatDate.format(order.getDateDelivery()));
        collapsingToolbar.setTitle(InfoStorage.formatDate.format(order.getDateDelivery()));
        orderAddress.setText(order.getOrderAddress() == null ? "" : order.getOrderAddress().getShortString());
        String comment = String.valueOf(order.getDeliveryInfo().isEmpty() ? getResources().getString(R.string.text_no_comments) : order.getDeliveryInfo());
        orderComments.setText(comment);
        orderItemName.setText(order.getName());
        orderCall.setText(order.getDeliveryPhone());
        orderCallClick.setOnClickListener(v -> mPresenter.callDelivery());

        showOrderRows(order.getOrderRows());

        orderDiscount.setText("" + InfoStorage.dfPercent.format(order.getDiscountPercent()) + "%");
        orderDiscountSum.setText(InfoStorage.dfMoneyLight.format(order.getDiscountSum()));
        orderTotalCount.setText("");
        orderTotalSum.setText(InfoStorage.dfMoney.format(order.getSumOrder()));

        fab.setOnClickListener(v -> mPresenter.openChoiceMap());

        SupportMapFragment fragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.container_map, fragment).commit();
        fragment.getMapAsync(map -> {
            // Updates the location and zoom of the MapView
            this.mGoogleMap = map;
            mPresenter.getMarker();
        });

    }


    @Override
    public void call(String phone) {

        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        } catch (RuntimeException e) {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        }
    }

    @Override
    public void closeFragment() {
        if (getActivity() != null) getActivity().finish();
    }


    @Override
    public void openMap(Order order, int index, double latitude, double longitude) {
        switch (index) {
            case 0:
                String uriGoogle = String.format(Locale.US, "http://maps.google.com/maps?daddr=%s,%s(%s)", order.getLocation().getDestinationLatString(), order.getLocation().getDestinationLngString(), order.getOrderAddress().getGeoAddress());
                Intent intentG = new Intent(Intent.ACTION_VIEW, Uri.parse(uriGoogle));
                intentG.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intentG);
                break;
            case 1:
                String uriYandex = String.format(Locale.US, "yandexmaps://maps.yandex.ru/?rtext=%s,%s~%S,%S", latitude, longitude, order.getLocation().getDestinationLatString(), order.getLocation().getDestinationLngString());
                Uri uri = Uri.parse(uriYandex);
                Intent intentY = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intentY);
                } catch (Exception e) {
                    new MaterialDialog.Builder(getContext())
                            .title(R.string.text_setup_yandex_map)
                            .content(R.string.text_app_yandex_map_not_found)
                            .positiveText(R.string.text_yes)
                            .negativeText(R.string.text_cancel)
                            .onPositive((dialog1, which) -> mPresenter.setupYaMap())
                            .show();
                }


                break;
        }
    }

    @Override
    public void showChoiceMap() {
        final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter((dialog, index1, item) -> {
            dialog.dismiss();
            mPresenter.openMap(index1);
        });
        adapter.add(new MaterialSimpleListItem.Builder(getActivity())
                .content("Google maps")
                .icon(R.drawable.vector_drawable_ic_map_google)
                .backgroundColor(Color.WHITE)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(getActivity())
                .content("Яндекс.Карты")
                .icon(R.drawable.ic_yandex_map)
                .backgroundColor(Color.WHITE)
                .build());

        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_what_use)
                .adapter(adapter, null)
                .show();
    }

    @Override
    public void showMarker(Order order, String currentLatitude, String currentLongitude) {

        if (order.getLocation().getDestinationLat() != 0 && order.getLocation().getDestinationLng() != 0) {
            LatLng latLng = new LatLng(order.getLocation().getDestinationLat(), order.getLocation().getDestinationLng());

            if (currentPositionMarker == null) {
                currentPositionMarker = new MarkerOptions();

                currentPositionMarker.position(latLng)
                        .title(order.getNumString() + "  " + formatDate.format(order.getDateDelivery()))
                        .visible(true)
                        .snippet(dfMoney.format(order.getTotalSum()))
                        .icon(BitmapDescriptorFactory.fromBitmap(order.isFree()?mPresenter.getBitMapFreeMarker():mPresenter.getBitMapMyMarker(order)));
                currentLocationMarker = mGoogleMap.addMarker(currentPositionMarker);
            }

            if (currentLocationMarker != null)
                currentLocationMarker.setPosition(latLng);

            currentPositionMarker.position(latLng);

            if (!currentLatitude.isEmpty()) {
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(Double.valueOf(currentLatitude), Double.valueOf(currentLongitude)))
                                .title(getString(R.string.text_address_point_delivery))
                                .visible(true)
                                .icon(BitmapDescriptorFactory.fromBitmap(icMarkerPlace))
                );

            }


            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
            mGoogleMap.animateCamera(cameraUpdate);

        } else showMessage(getResources().getString(R.string.text_not_found_geo_map));

    }

    @Override
    public void setupYaMap() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "ru.yandex.yandexmaps")));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "ru.yandex.yandexmaps")));
        }
    }

    @Override
    public void showChoiceCancel(List<Cancel> list) {

        ArrayList<String> array = new ArrayList<>();
        hashCancel.clear();
        for (Cancel c : list) {
            hashCancel.put(c.getName(), c.getId());
            array.add(c.getName());
        }

        new MaterialDialog.Builder(getActivity())
                .title(R.string.text_reason_cancel_order)
                .items(array)
                .itemsCallbackSingleChoice(2, (dialog, view, which, text) -> {
                    showMessage(which + ": " + text + " ");
                    mPresenter.choiceItemCancel(hashCancel.get(text));
                    return true;
                })
                .negativeText(R.string.text_cancel)
                .positiveText(R.string.text_select)
                .show();

    }

    @Override
    public void showCompleteOrder() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.text_complete_order)
                .content(getString(R.string.text_confirm_order) + " " + InfoStorage.dfMoney.format(mPresenter.getOrder().getTotalSum()))
                .positiveText(R.string.text_yes)
                .negativeText(R.string.text_cancel)
                .onPositive((dialog1, which) -> mPresenter.completeOrder())
                .show();

    }

    private void showOrderRows(List<OrderRow> rows) {

        orderContainerRow.removeAllViews();
        for (OrderRow row : rows) {
            View orderRowView = LayoutInflater.from(getActivity()).inflate(R.layout.item_order_row, orderContainerRow, false);
            TextView tvOrderRowName = (TextView) orderRowView.findViewById(R.id.tvOrderRowName);
            TextView tvOrderRowCount = (TextView) orderRowView.findViewById(R.id.tvOrderRowCount);
            TextView tvOrderRowSum = (TextView) orderRowView.findViewById(R.id.tvOrderRowSum);

            tvOrderRowName.setText(row.getName());
            tvOrderRowCount.setText(InfoStorage.dfCount.format(row.getCount()));
            tvOrderRowSum.setText(InfoStorage.dfMoneyLight.format(row.getTotalSum()));

            orderContainerRow.addView(orderRowView);
        }


    }

    private void showDialogAttach() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.text_dialog_title_attach_order)
                .content(mPresenter.getOrder().getOrderAddress() == null ? "" : mPresenter.getOrder().getOrderAddress().getShortString())
                .positiveText(R.string.text_yes)
                .negativeText(R.string.text_cancel)
                .onPositive((dialog1, which) -> mPresenter.clickAction(ATTACH_ORDER))
                .show();

    }

    private void showDialogDetach() {

        new MaterialDialog.Builder(getContext())
                .title(R.string.text_dialog_cancel_order)
                .content(mPresenter.getOrder().getOrderAddress() == null ? "" : mPresenter.getOrder().getOrderAddress().getShortString())
                .positiveText(R.string.text_yes)
                .negativeText(R.string.text_cancel)
                .onPositive((dialog1, which) -> mPresenter.clickAction(DETACH_ORDER))
                .show();

    }

    @Override
    public void setProgress(boolean progress) {

        if (progress) pd.show();
        else pd.dismiss();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.setView(null);
    }
}

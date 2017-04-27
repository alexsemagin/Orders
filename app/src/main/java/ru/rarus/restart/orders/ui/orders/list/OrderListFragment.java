package ru.rarus.restart.orders.ui.orders.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.MainActivity;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.order.OrderActivity;
import ru.rarus.restart.orders.ui.order.add.OrderAddActivity;
import ru.rarus.restart.orders.ui.orders.flexible.DeliveryItem;
import ru.rarus.restart.orders.ui.orders.map.OrdersMapFragment;

import static ru.rarus.restart.orders.ui.orders.list.OrderListPresenter.SORT_DIS;
import static ru.rarus.restart.orders.ui.orders.list.OrderListPresenter.SORT_SUM;
import static ru.rarus.restart.orders.ui.orders.list.OrderListPresenter.SORT_TIME;


public class OrderListFragment extends BaseFragment implements OrdersView, FlexibleAdapter.OnItemMoveListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.tlOrders)
    TabLayout tlOrders;

    @BindView(R.id.container_view_pager)
    ViewPager viewPager;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeLayout;


    @BindView(R.id.action_add_new)
    com.getbase.floatingactionbutton.FloatingActionButton actionAddNew;
    @BindView(R.id.action__open_map)
    com.getbase.floatingactionbutton.FloatingActionButton actionOpenMap;


    private OrderListPresenter mPresenter;
    private OrdersViewPagerAdapter ordersViewPagerAdapter;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new OrderListPresenter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.title_order_list);

        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.primary));
        swipeLayout.setOnRefreshListener(() -> mPresenter.getData(true, 0));

        ordersViewPagerAdapter = new OrdersViewPagerAdapter(this::onItemClick, getActivity(), this);
        ordersViewPagerAdapter.setRefreshAction(() -> mPresenter.getData(false, 0));

        viewPager = (ViewPager) view.findViewById(R.id.container_view_pager);
        viewPager.setAdapter(ordersViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        tlOrders = (TabLayout) view.findViewById(R.id.tlOrders);
        tlOrders.setupWithViewPager(viewPager);

        mPresenter.setView(this);
        mPresenter.getData(false, 0);

        actionAddNew.setOnClickListener(v -> mPresenter.clickFab());
        actionOpenMap.setOnClickListener(v -> mPresenter.clickOpenMap());

    }

    private boolean onToolbarMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.sort_by_distance:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(SORT_DIS);
                return true;
            case R.id.sort_by_sum:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(SORT_SUM);
                return true;
            case R.id.sort_by_time:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(SORT_TIME);
                return true;
        }
        return false;
    }

    @Override
    public void showMyOrders(List<AbstractFlexibleItem> orders) {
        ordersViewPagerAdapter.setMyOrders(orders);
    }

    @Override
    public void showFreeOrders(List<AbstractFlexibleItem> orders) {
        setProgress(false);
        ordersViewPagerAdapter.setFreeOrders(orders);
    }

    @Override
    public void setPlaceHolder(int id, boolean show) {
        ordersViewPagerAdapter.setPlaceHolder(id, show);
    }

    @Override
    public void openOrder(String orderId) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        Bundle args = new Bundle();
        args.putString("ID", orderId);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void openMap() {
        Fragment fragment = getActivity().getSupportFragmentManager()
                .findFragmentById(R.id
                        .container);
        if (fragment instanceof OrderListFragment) {
            ((MainActivity) getActivity()).addFragment(OrdersMapFragment.class, null);
        } else {
            ((MainActivity) getActivity()).detachFragment(OrdersMapFragment.class);
        }
    }

    @Override
    public void addOrder() {
        Intent intent = new Intent(getActivity(), OrderAddActivity.class);
        startActivity(intent);
    }


    private void onItemClick(String idData, int position) {
        mPresenter.onClickOrder(idData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.setView(null);
    }

    @Override
    public void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        swipeLayout.setEnabled(actionState == ItemTouchHelper.ACTION_STATE_IDLE);
    }

    @Override
    public boolean shouldMoveItem(int fromPosition, int toPosition) {
        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < ordersViewPagerAdapter.getMyAdapter().getItemCount(); i++) {
            DeliveryItem item = (DeliveryItem) ordersViewPagerAdapter.getMyAdapter().getItem(i);
            list.add(item.getIdItem());
        }
        mPresenter.setNewSortMyOrders(list);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        toolbar.getMenu().clear();
        if (position == 1) {
            toolbar.inflateMenu(R.menu.menu_orders);
            toolbar.setOnMenuItemClickListener(this::onToolbarMenuItemClick);

            MenuItem sortBySum = toolbar.getMenu().findItem(R.id.sort_by_sum);
            MenuItem sortByDistance = toolbar.getMenu().findItem(R.id.sort_by_distance);
            MenuItem sortByTime = toolbar.getMenu().findItem(R.id.sort_by_time);

            switch (mPresenter.getCurrentSort()) {
                case SORT_TIME:
                    sortByTime.setChecked(true);
                    break;
                case SORT_SUM:
                    sortBySum.setChecked(true);
                    break;
                case SORT_DIS:
                    sortByDistance.setChecked(true);
                    break;
            }
        }
        //else toolbar.inflateMenu(R.menu.menu_orders);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

package ru.rarus.restart.orders.ui.orders.list;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.orders.flexible.DeliveryAdapter;
import rx.functions.Action0;


class OrdersViewPagerAdapter extends PagerAdapter {

    public static final int ORDERS_PAGES_COUNT = 2;

    private Context mContext;


    private DeliveryAdapter myOrdersAdapter;
    private DeliveryAdapter freeOrdersAdapter;
    private OrderListViewHolder myOrdersHolder;
    private OrderListViewHolder freeOrdersHolder;

    private Action0 refreshAction;

    OrdersViewPagerAdapter(DeliveryAdapter.OnItemClickListener listener, FragmentActivity activity, OrderListFragment orderListFragment) {

        myOrdersAdapter = new DeliveryAdapter(activity, listener, true);
        myOrdersAdapter.addListener(orderListFragment);
        // Experimenting NEW features (v5.0.0)
        myOrdersAdapter.setOnlyEntryAnimation(true)
                .setAnimationInterpolator(new DecelerateInterpolator())
                .setAnimationInitialDelay(500L)
                .setAnimationDelay(70L);

        freeOrdersAdapter = new DeliveryAdapter(activity, listener, false);
        // freeOrdersAdapter.addListener(orderListFragment);
        // Experimenting NEW features (v5.0.0)
        freeOrdersAdapter.setOnlyEntryAnimation(true)
                .setAnimationInterpolator(new DecelerateInterpolator())
                .setAnimationInitialDelay(500L)
                .setAnimationDelay(70L);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);

    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public int getCount() {
        return ORDERS_PAGES_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Мои";
        } else {
            return "Свободные";
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.orders_page, container, false);
//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_recycler_view, container, false);
        OrderListViewHolder holder = new OrderListViewHolder(view);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        holder.rvOrders.addItemDecoration(new DividerItemDecoration(container.getContext(), LinearLayoutManager.VERTICAL));
        holder.rvOrders.setItemAnimator(itemAnimator);
        holder.rvOrders.setLayoutManager(new LinearLayoutManager(container.getContext()));

        if (position == 0) {
            myOrdersHolder = holder;
            myOrdersHolder.rvOrders.setItemViewCacheSize(0); //Setting ViewCache to 0 (default=2) will animate items better while scrolling down+up with LinearLayout
//            myOrdersHolder.rvOrders.setLayoutManager(createNewStaggeredGridLayoutManager());
            myOrdersHolder.rvOrders.setAdapter(myOrdersAdapter);
            //myOrdersHolder.rvOrders.setHasFixedSize(true); //Size of RV will not change

            // After Adapter is attached to RecyclerView
            myOrdersAdapter.setLongPressDragEnabled(true);

            // Add 2 Scrollable Headers
            // mAdapter.addScrollableHeader(new ScrollableUseCaseItem("1111","2222"));

            // Experimenting NEW features (v5.0.0)
            myOrdersAdapter.setLongPressDragEnabled(true) //Enable long press to drag items
                    .setHandleDragEnabled(true) //Enable drag using handle view
                    .setSwipeEnabled(true); //Enable swipe items


        } else {
            freeOrdersHolder = holder;
            holder.rvOrders.setAdapter(freeOrdersAdapter);

        }
        // holder.btnRefresh.setOnClickListener(this::onRefreshClick);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    void setRefreshAction(Action0 refreshAction) {
        this.refreshAction = refreshAction;
    }

    void setMyOrders(List<AbstractFlexibleItem> myOrders) {
        setOrders(myOrdersHolder, myOrders.size());
//       myOrdersAdapter.updateDataSet(myOrders,true);
//       myOrdersAdapter.notifyDataSetChanged();
        myOrdersAdapter.clear();
        myOrdersAdapter.addItems(0, myOrders);
    }

    void setFreeOrders(List<AbstractFlexibleItem> freeOrders) {

        setOrders(freeOrdersHolder, freeOrders.size());
//        freeOrdersAdapter.updateDataSet(freeOrders,true);
//        freeOrdersAdapter.notifyDataSetChanged();
        freeOrdersAdapter.clear();
        freeOrdersAdapter.addItems(0, freeOrders);
    }

    private void setOrders(OrderListViewHolder holder, int size) {

        if (holder != null) {
            if (size > 0) {
                holder.mPlaceHolder.removeAllViews();
                holder.rvOrders.setVisibility(View.VISIBLE);

            } else {
                holder.rvOrders.setVisibility(View.GONE);
                setPlaceHolder(holder.mPlaceHolder, R.layout.placeholder_no_data_orders, true);
            }
        }
    }


    private void setPlaceHolder(ViewGroup viewGroup, int idPlaceHolder, boolean show) {

        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup.setVisibility(show ? View.VISIBLE : View.GONE);
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(idPlaceHolder, viewGroup, false);
            viewGroup.addView(view);
        }
    }

    private void onRefreshClick(View view) {
        if (refreshAction != null) {
            refreshAction.call();
        }
    }

    void setPlaceHolder(int id, boolean show) {

        if (myOrdersAdapter.getItemCount() == 0) {
            myOrdersHolder.rvOrders.setVisibility(View.GONE);
            setPlaceHolder(myOrdersHolder.mPlaceHolder, id, true);
        }

        if (freeOrdersAdapter.getItemCount() == 0) {
            freeOrdersHolder.rvOrders.setVisibility(View.GONE);
            setPlaceHolder(freeOrdersHolder.mPlaceHolder, id, true);
        }

    }

    public DeliveryAdapter getMyAdapter() {
        return myOrdersAdapter;
    }

    private class OrderListViewHolder {
        final View pageView;
        final RecyclerView rvOrders;
        final ViewGroup mPlaceHolder;

        private OrderListViewHolder(View pageView) {
            this.pageView = pageView;
            rvOrders = (RecyclerView) pageView.findViewById(R.id.recycler_view);
            mPlaceHolder = (ViewGroup) pageView.findViewById(R.id.container_placeholder);
        }
    }


}

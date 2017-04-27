package ru.rarus.restart.orders.ui.orders.flexible;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;


import eu.davidea.flexibleadapter.utils.DrawableUtils;
import ru.rarus.restart.orders.R;


public class DeliveryAdapter extends FlexibleAdapter<AbstractFlexibleItem> {

    private OnItemClickListener mListener;
    private boolean isDrag = false;

    public DeliveryAdapter(Activity activity, OnItemClickListener listener, boolean isDrag) {
        //true = Items implement hashCode() and have stableIds!
        super(new ArrayList<>(), activity, true);
        mListener = listener;
        this.isDrag = isDrag;
    }


    /*
     * HEADER/FOOTER VIEW
     * This method show how to add Header/Footer View as it was for ListView.
     * The secret is the position! 0 for Header; itemCount for Footer ;-)
     * The view is represented by a custom Item type to better represent any dynamic content.
     */
    public void showLayoutInfo(boolean scrollToPosition) {
        if (!hasSearchText()) {
            //Define Example View
            final ScrollableLayoutItem item = new ScrollableLayoutItem("LAY-L");
//			if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
//				item.setId("LAY-S");
//				item.setTitle(mRecyclerView.getContext().getString(R.string.staggered_layout));
//			} else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
//				item.setId("LAY-G");
//				item.setTitle(mRecyclerView.getContext().getString(R.string.grid_layout));
//			} else {
            item.setTitle("Linear Layout");
//			}
            item.setSubtitle(mRecyclerView.getContext().getString(
                    R.string.columns,
                    String.valueOf(eu.davidea.flexibleadapter.utils.Utils.getSpanCount(mRecyclerView.getLayoutManager())))
            );
            // addScrollableHeaderWithDelay(item, 500L, scrollToPosition);
            // removeScrollableHeaderWithDelay(item, 3000L);
        }
    }

    /**
     * METHOD A - NEW! Via Model objects. In this case you don't need to implement this method!
     * METHOD B - You override and implement this method as you prefer (don't call super).
     */
    @Override
    public int getItemViewType(int position) {
        IFlexible item = getItem(position);
        if (item instanceof ScrollableUseCaseItem) return R.layout.recycler_scrollable_usecase_item;
        else if (item instanceof ScrollableLayoutItem)
            return R.layout.recycler_scrollable_layout_item;
            // else return R.layout.recycler_overall_item;
        else return R.layout.item_base_simple_adapter;
    }

    /**
     * METHOD A - NEW! Via Model objects. In this case you don't need to implement this method!
     * METHOD B - You override and implement this method as you prefer (don't call super).
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case R.layout.recycler_scrollable_usecase_item:
                return new ScrollableUseCaseItem.UCViewHolder(
                        mInflater.inflate(viewType, parent, false), this);

            case R.layout.recycler_scrollable_layout_item:
                return new ScrollableLayoutItem.LayoutViewHolder(
                        mInflater.inflate(viewType, parent, false), this);
            default:
                return new DeliveryItem.LabelViewHolder(
                        mInflater.inflate(viewType, parent, false), this);
        }
    }

    /**
     * METHOD A - NEW! Via Model objects. In this case you don't need to implement this method!
     * METHOD B - You override and implement this method as you prefer (don't call super).
     * <p>
     * Using Method B, some methods need to be called by the user, see bottom of this method!
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payload) {
        int viewType = getItemViewType(position);
        Context context = holder.itemView.getContext();


        if (viewType == R.layout.recycler_scrollable_usecase_item) {
            ScrollableUseCaseItem item = (ScrollableUseCaseItem) getItem(position);
            ScrollableUseCaseItem.UCViewHolder vHolder = (ScrollableUseCaseItem.UCViewHolder) holder;
            assert item != null;

            DrawableUtils.setBackgroundCompat(holder.itemView, DrawableUtils.getRippleDrawable(
                    DrawableUtils.getColorDrawable(context.getResources().getColor(R.color.md_blue_grey_50)),
                    DrawableUtils.getColorControlHighlight(context))
            );
            vHolder.mTitle.setText(Utils.fromHtmlCompat(item.getTitle()));
            vHolder.mSubtitle.setText(Utils.fromHtmlCompat(item.getSubtitle()));

            //Support for StaggeredGridLayoutManager
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
                Log.d("LayoutItem", "LayoutItem configured fullSpan for StaggeredGridLayout");
            }

        } else if (viewType == R.layout.recycler_scrollable_layout_item) {
            ScrollableLayoutItem item = (ScrollableLayoutItem) getItem(position);
            ScrollableLayoutItem.LayoutViewHolder vHolder = (ScrollableLayoutItem.LayoutViewHolder) holder;
            assert item != null;

            vHolder.mTitle.setSelected(true);//For marquee
            vHolder.mTitle.setText(item.getTitle());
            vHolder.mSubtitle.setText(item.getSubtitle());

            //Support for StaggeredGridLayoutManager
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
                Log.d("LayoutItem", "LayoutItem configured fullSpan for StaggeredGridLayout");
            }

        } else if (viewType == R.layout.item_base_simple_adapter) {

            DeliveryItem item = (DeliveryItem) getItem(position);
            DeliveryItem.LabelViewHolder vHolder = (DeliveryItem.LabelViewHolder) holder;

            vHolder.drag.setVisibility(isDrag? View.VISIBLE:View.GONE);
            vHolder.drag_padding.setVisibility(isDrag? View.GONE:View.VISIBLE);
            vHolder.baseSimpleTvTitle.setText(item.getMainTitle());
            vHolder.baseSimpleTvRightTop.setText(item.getRightTopTitle());
            vHolder.baseSimpleTvTopLeft.setText(item.getTopLeftTitle());

            vHolder.baseSimpleTvSubTitle.setText(item.getSubTitle());
            vHolder.baseSimpleTvTopCenter.setText(String.valueOf(item.getTopCenterTitle()));
            vHolder.baseSimpleTvRightMedium.setText(String.valueOf(item.getRightMediumTitle()));
            vHolder.baseSimpleTvRightBottom.setText(String.valueOf(item.getRightBottomTitle()));

            if (item.getIcMain() != null) vHolder.icMain.setImageDrawable(item.getIcMain());

            if (item.getTextColorTopLeftTitle() != 0)
                vHolder.baseSimpleTvTopLeft.setTextColor(item.getTextColorTopLeftTitle());
            if (item.getTextColorRightTopTitle() != 0)
                vHolder.baseSimpleTvRightTop.setTextColor(item.getTextColorRightTopTitle());

            vHolder.itemView.setOnClickListener((v) -> {
                if (mListener != null) {
                    mListener.onItemClick(item.getIdItem(), position);
                }
            });
        }


        // IMPORTANT!!!
        // With method B, animateView() needs to be called by the user!
        // With method A, the call is handled by the Adapter
        animateView(holder, position);
        // Same concept for EndlessScrolling and View activation:
        // - onLoadMore(position);
        // - holder.itemView.setActivated(isSelected(position));
    }


    public interface OnItemClickListener {
        void onItemClick(String id, int pos);
    }

}
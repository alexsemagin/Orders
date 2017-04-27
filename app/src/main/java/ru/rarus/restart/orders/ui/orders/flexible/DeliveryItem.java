package ru.rarus.restart.orders.ui.orders.flexible;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

import eu.davidea.viewholders.FlexibleViewHolder;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.ui.abscracts.UiUtils;

public class DeliveryItem extends AbstractFlexibleItem<DeliveryItem.LabelViewHolder> {



    private int id;
    private int index;

    private String idItem;

    private String mainTitle;
    private Color textColorMainTitleId;

    private String subTitle;
    private int textColorSubTitleId;

    private String topLeftTitle;
    private int textColorTopLeftTitle;

    private String rightMediumTitle;
    private int textColorRightMediumTitle;

    private String rightBottomTitle;
    private int textColorRightBottomTitle;

    private String rightTopTitle;
    private int textColorRightTopTitle;

    private String topCenterTitle;
    private int textColorTopCenter;

    private Drawable icMain;


    public DeliveryItem() {
        setSelectable(false);
        //Allow dragging
        setDraggable(true);
    }


    public DeliveryItem withEnabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }


    public static List<AbstractFlexibleItem> getList(List<Order> listOrder, Context context) {
        List<AbstractFlexibleItem> list = new ArrayList<>();
        //int i=222;
        for (Order c : listOrder) {
            DeliveryItem bs = new DeliveryItem();
            //bs.setId(c.getId());
            bs.setIdItem(c.getId());
            bs.setIndex(c.getLocation().getIndex());
            bs.setMainTitle(c.getOrderAddress() == null ? "" : c.getOrderAddress().getShortString());
            bs.setSubTitle(c.getNumString());

            bs.setRightMediumTitle(InfoStorage.dfMoneyLight.format(c.getTotalSum()));
            bs.setRightBottomTitle(RestartSettings.sCurrency);
            bs.setRightTopTitle(InfoStorage.formatDateLight.format(c.getDateDelivery()));
            bs.setTextColorRightTopTitle(ContextCompat.getColor(context, R.color.accent));
            if (c.getLocation() == null) bs.setTopCenterTitle("");
            else bs.setTopCenterTitle(c.getLocation().getStringLocation());

            switch (c.getStatusPay()) {
                case Order.STATUS_PAY.NOT_PAY:
                    bs.setTopLeftTitle(context.getResources().getString(R.string.text_order_pay_ok));
                    bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.primary));
                    bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_sum_no_pay));


                    break;
                case Order.STATUS_PAY.PAID:
                    bs.setTopLeftTitle(context.getResources().getString(R.string.text_pay_complete));
                    bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.accent));
                    bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_sum_pay));


                    break;
                default:
                    bs.setTopLeftTitle(context.getString(R.string.text_no_info));
                    break;
            }

            if (!c.isFree()) {

                switch (c.getLocation().getIndex()) {
                    case 0:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_1));
                        break;
                    case 1:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_2));
                        break;
                    case 2:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_3));
                        break;
                    case 3:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_4));
                        break;
                    case 4:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_5));
                        break;
                    case 5:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_6));
                        break;
                    case 6:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_7));
                        break;
                    case 7:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_8));
                        break;
                    case 8:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_9));
                        break;
                    case 9:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_10));
                        break;
                    case 10:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_11));
                        break;
                    case 11:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_12));
                        break;
                    case 12:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_13));
                        break;
                    case 13:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_14));
                        break;
                    case 14:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_15));
                        break;
                    case 15:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_16));
                        break;
                    case 16:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_17));
                        break;
                    case 17:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_18));
                        break;
                    case 18:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_19));
                        break;
                    case 19:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_20));
                        break;
                    default:
                        bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable
                                .vector_drawable_20_plus));
                        break;
                }
            } else bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_ic_add_location));


            list.add(bs);

        }
        return list;

    }




    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getRightBottomTitle() {
        return rightBottomTitle;
    }

    public void setRightBottomTitle(String rightBottomTitle) {
        this.rightBottomTitle = rightBottomTitle;
    }

    public int getTextColorRightBottomTitle() {
        return textColorRightBottomTitle;
    }

    public void setTextColorRightBottomTitle(int textColorRightBottomTitle) {
        this.textColorRightBottomTitle = textColorRightBottomTitle;
    }

    public Color getTextColorMainTitleId() {
        return textColorMainTitleId;
    }

    public void setTextColorMainTitleId(Color textColorMainTitleId) {
        this.textColorMainTitleId = textColorMainTitleId;
    }

    public int getTextColorSubTitleId() {
        return textColorSubTitleId;
    }

    public void setTextColorSubTitleId(int textColorSubTitleId) {
        this.textColorSubTitleId = textColorSubTitleId;
    }

    public int getTextColorTopLeftTitle() {
        return textColorTopLeftTitle;
    }

    public void setTextColorTopLeftTitle(int textColorTopLeftTitle) {
        this.textColorTopLeftTitle = textColorTopLeftTitle;
    }

    public int getTextColorRightMediumTitle() {
        return textColorRightMediumTitle;
    }

    public void setTextColorRightMediumTitle(int textColorRightMediumTitle) {
        this.textColorRightMediumTitle = textColorRightMediumTitle;
    }

    public int getTextColorRightTopTitle() {
        return textColorRightTopTitle;
    }

    public void setTextColorRightTopTitle(int textColorRightTopTitle) {
        this.textColorRightTopTitle = textColorRightTopTitle;
    }

    public String getTopCenterTitle() {
        return topCenterTitle;
    }

    public void setTopCenterTitle(String topCenterTitle) {
        this.topCenterTitle = topCenterTitle;
    }

    public int getTextColorTopCenter() {
        return textColorTopCenter;
    }

    public void setTextColorTopCenter(int textColorTopCenter) {
        this.textColorTopCenter = textColorTopCenter;
    }


    public Drawable getIcMain() {
        return icMain;
    }

    public void setIcMain(Drawable icMain) {
        this.icMain = icMain;
    }


    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }


    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setRightMediumTitle(String rightMediumTitle) {
        this.rightMediumTitle = rightMediumTitle;
    }

    public String getRightTopTitle() {
        return rightTopTitle;
    }

    public void setRightTopTitle(String rightTopTitle) {
        this.rightTopTitle = rightTopTitle;
    }

    public String getRightMediumTitle() {
        return rightMediumTitle;
    }


    public String getTopLeftTitle() {
        return topLeftTitle;
    }

    public void setTopLeftTitle(String topLeftTitle) {
        this.topLeftTitle = topLeftTitle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryItem that = (DeliveryItem) o;
        return idItem.hashCode() == that.idItem.hashCode();
    }

    @Override
    public int hashCode() {
        //Log.d("QQQ", "hashCode"+idItem.hashCode() +"   "+mainTitle);
        return idItem.hashCode();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static class LabelViewHolder extends FlexibleViewHolder {

        public TextView mTitle;
        public TextView mSubtitle;
        public ImageView mIcon;


        @BindView(R.id.icMain)
        public ImageView icMain;
        @BindView(R.id.base_simple_tv_top_left)
        public TextView baseSimpleTvTopLeft;
        @BindView(R.id.base_simple_tv_top_center)
        public TextView baseSimpleTvTopCenter;
        @BindView(R.id.base_simple_tv_title)
        public TextView baseSimpleTvTitle;
        @BindView(R.id.base_simple_tv_sub_title)
        public TextView baseSimpleTvSubTitle;
        @BindView(R.id.base_simple_tv_right_top)
        public TextView baseSimpleTvRightTop;
        @BindView(R.id.base_simple_tv_right_medium)
        public TextView baseSimpleTvRightMedium;
        @BindView(R.id.base_simple_tv_right_bottom)
        public TextView baseSimpleTvRightBottom;
        @BindView(R.id.drag)
        public FrameLayout drag;
        @BindView(R.id.drag_padding)
        public View drag_padding;

        public LabelViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle = (TextView) view.findViewById(R.id.title);
            mSubtitle = (TextView) view.findViewById(R.id.subtitle);
            mIcon = (ImageView) view.findViewById(R.id.label_background);

            ButterKnife.bind(this, itemView);

            setDragHandleView(drag);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            if (mAdapter.getRecyclerView().getLayoutManager() instanceof GridLayoutManager) {
                if (position % 2 != 0)
                    AnimatorHelper.slideInFromRightAnimator(animators, itemView, mAdapter.getRecyclerView(), 0.5f);
                else
                    AnimatorHelper.slideInFromLeftAnimator(animators, itemView, mAdapter.getRecyclerView(), 0.5f);
            } else {
                if (isForward)
                    AnimatorHelper.slideInFromBottomAnimator(animators, itemView, mAdapter.getRecyclerView());
                else
                    AnimatorHelper.slideInFromTopAnimator(animators, itemView, mAdapter.getRecyclerView());
            }
        }

        @Override
        protected void setDragHandleView(@NonNull View view) {
            if (mAdapter.isHandleDragEnabled()) {
                view.setVisibility(View.VISIBLE);
                super.setDragHandleView(view);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

}
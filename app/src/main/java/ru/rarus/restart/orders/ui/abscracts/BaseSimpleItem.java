package ru.rarus.restart.orders.ui.abscracts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;

import java.util.ArrayList;
import java.util.List;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.info.InfoStorage;



public class BaseSimpleItem  {

    private int id;

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



    public static List<BaseSimpleItem> getList(List<Order> listOrder, Context context) {
        List<BaseSimpleItem> list = new ArrayList<>();
        for (Order c : listOrder) {
            BaseSimpleItem bs = new BaseSimpleItem();
            bs.setIdItem(c.getId());
            bs.setMainTitle(c.getOrderAddress()==null?"":c.getOrderAddress().getShortString());
            bs.setSubTitle(c.getNumString());

            bs.setRightMediumTitle(InfoStorage.dfMoneyLight.format(c.getTotalSum()));
            bs.setRightBottomTitle("руб");
            bs.setRightTopTitle(InfoStorage.formatDateLight.format(c.getDateDelivery()));
            bs.setTextColorRightTopTitle(ContextCompat.getColor(context, R.color.accent));
            if(c.getLocation()==null) bs.setTopCenterTitle("");
            else  bs.setTopCenterTitle(c.getLocation().getStringLocation());

//            switch (c.getStatus()) {
//                case "received_delivery":
//                    bs.setTopLeftTitle(context.getResources().getString(R.string.text_order_pay_ok));
//                    bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.primary));
//                    bs.setIcMain(getDrawableRomRes(context,R.drawable.vector_drawable_sum_no_pay));
//
//
//                    break;
//                case "close":
//                    bs.setTopLeftTitle(context.getResources().getString(R.string.text_pay_complete));
//                    bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.accent));
//                    bs.setIcMain(getDrawableRomRes(context,R.drawable.vector_drawable_sum_pay));
//
//
//                    break;
//                default:
//                    bs.setTopLeftTitle(c.getStatus());
//                    break;
//            }

            bs.setTopLeftTitle(context.getResources().getString(R.string.text_order_pay_ok));
            bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.primary));
            bs.setIcMain(getDrawableRomRes(context,R.drawable.vector_drawable_sum_no_pay));


            list.add(bs);
        }
        return list;

    }

    private static Drawable getDrawableRomRes(Context context, int resId){

       return AppCompatDrawableManager.get().getDrawable(context, resId);
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




}

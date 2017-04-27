package ru.rarus.restart.orders.ui.account;


import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import ru.rarus.restart.orders.ui.abscracts.BaseSimpleItem;



public interface IAccountView extends BasePresenter.BaseView {

   

    void showCash(double cash);

    void showScore(double score);

    void setData(List<BaseSimpleItem> orders);

    void openDialog();

    void showData(List<AbstractFlexibleItem> mItems);
}

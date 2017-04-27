package ru.rarus.restart.orders.ui.account;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Operation;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.managers.HistoryManager;
import ru.rarus.restart.orders.di.managers.OrdersManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static ru.rarus.restart.orders.data.info.InfoStorage.dfMoney;

public class AccountPresenter extends BasePresenter<IAccountView> {


    @Inject
    RestartSettings mRestartSettings;

    @Inject
    OrdersManager ordersManager;

    @Inject
    HistoryManager mHistoryManager;

    @Inject
    Context mContext;

    static final int ORDERS_IN_WORK = 0;
    static final int ORDERS_CLOSED = 1;
    static final int ORDER_CANCELED = 2;

    private Float sumInCash=0f;
    private Float sumInCashBox=0f;

    private List<AbstractFlexibleItem> mItems = new ArrayList<>();

    private final ExpandableHeaderItem expandableWork;
    private final ExpandableHeaderItem expandableClosed;
    private final ExpandableHeaderItem expandableCanseled;

    AccountPresenter() {
        MyApp.getApp().getUserComponent().inject(this);
        mItems.add(expandableWork = newExpandableSectionItem(ORDERS_IN_WORK));
        mItems.add(expandableClosed = newExpandableSectionItem(ORDERS_CLOSED));
        mItems.add(expandableCanseled = newExpandableSectionItem(ORDER_CANCELED));

        sumInCashBox =mRestartSettings.getSumCashBox();

    }

    public double getCash() {
        return sumInCash;
    }

    public List<AbstractFlexibleItem> getmItems() {
        return mItems;
    }

    void getData(boolean update) {

        getView().setProgress(true);
        showSumTitle();

        ordersManager.getOrders(false, update)
                .map(list -> SubItem.getList(list, mContext, expandableWork))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getCloseOrders()
                        , e -> {
                            if (mView != null) {
                                mView.setProgress(false);
                                mView.showMessage(e.getMessage());
                                if (TextUtils.equals("Нет подключения", e.getMessage()))
                                    mView.setPlaceHolder(R.layout.placeholder_no_connect, true);
                            }
                            getCloseOrders();
                        }
                );
    }


    private void getCloseOrders() {
        ordersManager.getCloseOrdersFromDb()
                .map(list -> {
                    sumInCash = 0f;
                    for (Order e : list) sumInCash += e.getTotalSum().floatValue();
                    sumInCash=(sumInCash-sumInCashBox)<0?0:sumInCash-sumInCashBox;
                    return list;
                })
                .map(list -> SubItem.getList(list, mContext, expandableClosed))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            if (mView != null) {
                                showSumTitle();
                                mView.showData(mItems);
                                mView.setProgress(false);
                            }


                        }
                        , e -> {
                            if (mView != null) {
                                mView.showData(mItems);
                                showSumTitle();
                                mView.setProgress(false);
                                mView.showMessage(e.getMessage());
                                if (TextUtils.equals("Нет подключения", e.getMessage()))
                                    mView.setPlaceHolder(R.layout.placeholder_no_connect, true);
                            }
                        }
                );

    }

    private  void showSumTitle(){
        if (mView!=null)mView.showCash(sumInCash);
        if (mView!=null)mView.showScore(sumInCashBox);
    }


    private ExpandableHeaderItem newExpandableSectionItem(int type) {
        ExpandableHeaderItem expandableItem = new ExpandableHeaderItem(String.valueOf(type), type);
        switch (type) {
            case ORDERS_IN_WORK:
                expandableItem.setTitle(mContext.getString(R.string.account_orders_in_work));
                break;
            case ORDERS_CLOSED:
                expandableItem.setTitle(mContext.getString(R.string.account_orders_completed));
                break;
            case ORDER_CANCELED:
                expandableItem.setTitle(mContext.getString(R.string.account_orders_not_completed));
                break;
        }

        return expandableItem;
    }

    void dropCashAndTotalScore() {
        mRestartSettings.setSumCashBox(sumInCashBox+sumInCash);
        sumInCashBox = mRestartSettings.getSumCashBox();
        mHistoryManager.createStringOperation(dfMoney.format(sumInCash), Operation.Action.ACTION_PUT_CASH);
        sumInCash = 0f;
        showSumTitle();
    }

    void clickFab() {
        if (mView != null) mView.openDialog();
    }
}

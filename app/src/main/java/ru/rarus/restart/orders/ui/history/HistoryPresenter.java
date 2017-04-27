package ru.rarus.restart.orders.ui.history;


import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.di.managers.HistoryManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import rx.Observable;

public class HistoryPresenter extends BasePresenter<HistoryView> {

    @Inject
    HistoryManager historyManager;
    private int currentFilter = R.id.sort_by_all_actions;

    HistoryPresenter() {
        MyApp.getApp().getUserComponent().inject(this);
    }

    void getData() {
        historyManager.getOperationList()
                .flatMap(Observable::from)
                .filter(s -> currentFilter == R.id.sort_by_all_actions || currentFilter == s.getStringIdAction())
                .toList()
                .subscribe(list -> {
                    if (mView != null) mView.showData(list);
                }, e -> handleThrowable(e));
    }

    void setSort(int i) {
        currentFilter = i;
        getData();
    }

    int getCurrentFilter() {
        return currentFilter;
    }

}

package ru.rarus.restart.orders.ui.history;


import java.util.List;

import ru.rarus.restart.orders.data.entities.Operation;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;

public interface HistoryView extends BasePresenter.BaseView{

    void showData(List<Operation> list);
}

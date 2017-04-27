package ru.rarus.restart.orders.ui.order.add.callLog;

import java.util.List;

import ru.rarus.restart.orders.data.entities.CallLogOperation;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;



public interface CallLogInterface extends BasePresenter.BaseView{

    void checkPermissions();

    void showData(List<CallLogOperation> list);
}

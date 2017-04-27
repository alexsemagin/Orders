package ru.rarus.restart.orders.ui.history;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Operation;
import ru.rarus.restart.orders.ui.abscracts.BaseAdapter;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.order.DividerItemDecoration;


public class HistoryFragment extends BaseFragment implements HistoryView, BaseAdapter.OnItemClickListener<Operation> {

    private HistoryPresenter mPresenter;

    @BindView(R.id.rv_history)
    RecyclerView historyRV;
    Toolbar toolbar;

    private HistoryAdapter historyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new HistoryPresenter();
        historyAdapter = new HistoryAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.drawer_item_history);
        toolbar.inflateMenu(R.menu.menu_history);
        toolbar.setOnMenuItemClickListener(this::onToolbarMenuItemClick);

        MenuItem mSortBySignIn = toolbar.getMenu().findItem(R.id.sort_by_all_actions);
        MenuItem mSortByAllActions = toolbar.getMenu().findItem(R.id.sort_by_action_sign_in);
        MenuItem mSortBySignInOut = toolbar.getMenu().findItem(R.id.sort_by_action_sign_out);
        MenuItem mSortByOrderAttach = toolbar.getMenu().findItem(R.id.sort_by_action_order_attach);
        MenuItem mSortByOrderDetach = toolbar.getMenu().findItem(R.id.sort_by_action_order_detach);
        MenuItem mSortByOrderSuccess = toolbar.getMenu().findItem(R.id.sort_by_action_order_success);
        MenuItem mSortByOrderCanceled = toolbar.getMenu().findItem(R.id.sort_by_action_order_canceled);
        MenuItem mSortByPutCash = toolbar.getMenu().findItem(R.id.sort_by_action_put_cash);

        switch (mPresenter.getCurrentFilter()) {
            case R.id.sort_by_all_actions:
                mSortByAllActions.setChecked(true);
                break;
            case R.id.sort_by_action_sign_in:
                mSortBySignIn.setChecked(true);
                break;
            case R.id.sort_by_action_sign_out:
                mSortBySignInOut.setChecked(true);
                break;
            case R.id.sort_by_action_order_attach:
                mSortByOrderAttach.setChecked(true);
                break;
            case R.id.sort_by_action_order_detach:
                mSortByOrderDetach.setChecked(true);
                break;
            case R.id.sort_by_action_order_success:
                mSortByOrderSuccess.setChecked(true);
                break;
            case R.id.sort_by_action_order_canceled:
                mSortByOrderCanceled.setChecked(true);
                break;
            case R.id.sort_by_action_put_cash:
                mSortByPutCash.setChecked(true);
                break;
        }

        historyRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        historyRV.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        historyAdapter.setContext(this.getContext());
        historyRV.setAdapter(historyAdapter);

        mPresenter.setView(this);
        mPresenter.getData();
    }

    private boolean onToolbarMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.sort_by_all_actions:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_all_actions);
                return true;
            case R.id.sort_by_action_sign_in:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_sign_in);
                return true;
            case R.id.sort_by_action_sign_out:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_sign_out);
                return true;
            case R.id.sort_by_action_order_attach:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_order_attach);
                return true;
            case R.id.sort_by_action_order_detach:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_order_detach);
                return true;
            case R.id.sort_by_action_order_success:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_order_success);
                return true;
            case R.id.sort_by_action_order_canceled:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_order_canceled);
                return true;
            case R.id.sort_by_action_put_cash:
                menuItem.setChecked(!menuItem.isChecked());
                mPresenter.setSort(R.id.sort_by_action_put_cash);
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.getMenu().clear();
        mPresenter.setView(null);
    }

    @Override
    public void showData(List<Operation> list) {
        historyAdapter.setList(list);
    }

    @Override
    public void onItemClick(Operation data, int position) {

    }
}

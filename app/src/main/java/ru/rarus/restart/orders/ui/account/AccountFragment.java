package ru.rarus.restart.orders.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.abscracts.BaseSimpleItem;

import static ru.rarus.restart.orders.data.info.InfoStorage.dfMoney;


public class AccountFragment extends BaseFragment implements IAccountView {

    @BindView(R.id.account_tv_cash)
    TextView cash;
    @BindView(R.id.account_tv_total_score)
    TextView score;

    @BindView(R.id.fab_cash_drop)
    FloatingActionButton fabCashDrop;

    @BindView(R.id.expand_recycler_view)
    RecyclerView mRecyclerView;


    Toolbar toolbar;
    AccountPresenter presenter;


    Unbinder unbinder;
    private ExampleAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new AccountPresenter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.account_toolbar_title);
        toolbar.getMenu().clear();

        fabCashDrop.setOnClickListener(v -> presenter.clickFab());

        mRecyclerView.setLayoutManager(new SmoothScrollLinearLayoutManager(getActivity()));
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true); //Size of RV will not change
        // NOTE: Use default item animator 'canReuseUpdatedViewHolder()' will return true if
        // a Payload is provided. FlexibleAdapter is actually sending Payloads onItemChange.
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Custom divider item decorator
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
//                R.drawable.divider, 0)); //Increase to add gap between sections (Works only with LinearLayout!)


        // Experimenting NEW features (v5.0.0)
//        mAdapter.setLongPressDragEnabled(true) //Enable long press to drag items
//                .setHandleDragEnabled(true); //Enable handle drag
        //.setDisplayHeadersAtStartUp(true); //Show Headers at startUp: (not necessary if Headers are also Expandable)

        //if (mAdapter != null) mRecyclerView.setAdapter(mAdapter);
        presenter.setView(this);
        presenter.getData(false);



    }


    @Override
    public void openDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());

        MaterialDialog dropDialog = builder
                .title(R.string.text_title_cash)
                .content(getResources().getString(R.string.account_drop_promt_1)+" "+
                        dfMoney.format(presenter.getCash())+" "+
                        getResources().getString(R.string.account_drop_promt_2))
                .negativeText(R.string.account_no)
                .positiveText(R.string.account_yes)
                .onPositive((dialog, which) -> presenter.dropCashAndTotalScore())
                .onNegative((dialog, which) -> dialog.cancel())
                .build();

        dropDialog.show();
    }

    @Override
    public void showData(List<AbstractFlexibleItem> mItems) {

        //временно пока не исправиться ошибка в flexible adapter
        mAdapter = new ExampleAdapter(this, presenter.getmItems());
        mAdapter.expandItemsAtStartUp()
                .setAutoCollapseOnExpand(true)
                .setAutoScrollOnExpand(true);
        mAdapter.collapseAll();
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void showCash(double cash) {
        this.cash.setText(InfoStorage.dfMoney.format(cash));
    }

    @Override
    public void showScore(double score) {
        this.score.setText( InfoStorage.dfMoney.format(score));
    }


    @Override
    public void setData(List<BaseSimpleItem> orders) {
        Log.d("AccountFragment", "setData");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.setView(null);
        unbinder.unbind();
    }
}

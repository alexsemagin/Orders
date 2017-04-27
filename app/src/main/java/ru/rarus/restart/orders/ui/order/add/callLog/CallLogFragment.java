package ru.rarus.restart.orders.ui.order.add.callLog;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.CallLogOperation;
import ru.rarus.restart.orders.ui.abscracts.BaseAdapter;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.order.DividerItemDecoration;


public class CallLogFragment extends BaseFragment implements CallLogInterface, BaseAdapter.OnItemClickListener<CallLogOperation> {
    public final static int REQUEST_CALL_LOG = 777;
    public static final String INTENT_PHONE = "phone";
    private CallLogPresenter mPresenter;
    private CallLogAdapter callLogAdapter;


    Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_call_log)
    RecyclerView callLogRV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new CallLogPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.title_call_log);
        toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        callLogRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        callLogRV.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        callLogAdapter = new CallLogAdapter(this);
        callLogRV.setAdapter(callLogAdapter);

        mPresenter.setView(this);
        mPresenter.getData();
    }


    @Override
    public void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            showData(mPresenter.requestLogCallManager());
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CALL_LOG)) {
                if (getActivity().getCurrentFocus() != null)
                    Snackbar.make(getActivity().getCurrentFocus(), R.string.permission_call_log_rationale, Snackbar.LENGTH_LONG).show();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOG);
            }
        }
    }

    @Override
    public void showData(List<CallLogOperation> list) {
        callLogAdapter.setList(list);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showData(mPresenter.requestLogCallManager());
            } else {
                if (getActivity().getCurrentFocus() != null)
                Snackbar.make(getActivity().getCurrentFocus(), R.string.text_call_log_cancelled, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemClick(CallLogOperation data, int position) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_PHONE, data.getNumber());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}

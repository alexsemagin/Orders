package ru.rarus.restart.orders.ui.abscracts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import ru.rarus.restart.orders.R;


public class BaseFragment extends Fragment implements BasePresenter.BaseView {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void showMessage(String message ){
       // UiUtils.showSnakeBar(getActivity(), getView(), message);
        if(getView()!=null)Snackbar.make(getView(),message,Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showMessageDialog(String title, String message ){
        if (getActivity()!=null) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
            MaterialDialog dropDialog = builder
                    .title(title)
                    .content(message)
                    .positiveText(R.string.text_ok)
                    .onPositive((dialog, which) -> dialog.cancel())
                    .onNegative((dialog, which) -> dialog.cancel())
                    .build();
            dropDialog.show();
        }
    }

    @Override
    public void setProgress(boolean progress ){
        //Log.d("QQQ","setProgress "+progress);

        if (getView()!=null) {
            SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
//            SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
            if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(progress);

            ProgressBar mProgressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
            if (mProgressBar!=null) mProgressBar.setVisibility(progress?View.VISIBLE:View.GONE);
        }


    }



    @Override
    public void setPlaceHolder(int id, boolean show){

        if (getActivity()!=null && getView()!=null) {
            ViewGroup mPlaceHolder = (ViewGroup) getView().findViewById(R.id.container_placeholder);
            if (mPlaceHolder != null){
                mPlaceHolder.removeAllViews();
                mPlaceHolder.setVisibility(show?View.VISIBLE:View.GONE);
                getActivity().getLayoutInflater().inflate(id, mPlaceHolder, true);
            }
        }
    }


    @Override
    public void hidePlaceholders(){

        if (getActivity()!=null && getView()!=null) {
            ViewGroup  mPlaceHolder = (ViewGroup) getView().findViewById(R.id.container_placeholder);
            if (mPlaceHolder != null){
                mPlaceHolder.removeAllViews();
                mPlaceHolder.setVisibility(View.GONE);
            }
        }

    }



}

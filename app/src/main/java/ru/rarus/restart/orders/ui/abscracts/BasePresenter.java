package ru.rarus.restart.orders.ui.abscracts;



import android.text.TextUtils;

import ru.rarus.restart.orders.R;

public abstract class BasePresenter<V extends BasePresenter.BaseView> {

    protected V mView;

    public void setView(V view) {
        mView = view;

    }

    protected V getView() {
        return mView;
    }


    public void freeResources() {
        mView = null;
    }



    protected void handleThrowable(Throwable thr){
        if (mView != null){
            mView.setProgress(false);
            mView.showMessage(thr.getMessage());
            if(TextUtils.equals("Нет подключения",thr.getMessage())) mView.setPlaceHolder(R.layout.placeholder_no_connect,true);
        }

    }




    public interface BaseView {

        void showMessage(String message);

        void showMessageDialog(String title, String message);

        void setProgress(boolean update);

        void setPlaceHolder(int id, boolean show);

        void hidePlaceholders();

    }
}
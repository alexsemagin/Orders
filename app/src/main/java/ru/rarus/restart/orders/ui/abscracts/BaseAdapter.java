package ru.rarus.restart.orders.ui.abscracts;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mList = new ArrayList<T>();
    protected OnItemClickListener<T> mListener;

    public BaseAdapter(OnItemClickListener<T> listener) {
        if (listener == null) {
            Log.w("log", "BaseAdapter constructor: Listener is null");
        } else {
            mListener = listener;
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        VH viewHolder = onChildCreateViewHolder(parent, viewType);
        viewHolder.itemView.setOnClickListener((v) -> {
            if (mListener != null) {
                mListener.onItemClick(getItemAt(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    protected abstract VH onChildCreateViewHolder(ViewGroup parent, int viewType);

    public void setList(List<T> newList) {
        if (newList == null) {
            mList = Collections.emptyList();
        } else {
            mList = newList;
        }
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mListener;
    }

    public void setListener(OnItemClickListener<T> listener) {
        if (listener == null) {
            Log.w("log", "BaseAdapter: Listener is null");
        } else  {
            this.mListener = listener;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public T getItemAt(int position) {
        return mList.get(position);
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T data, int position);
    }
}
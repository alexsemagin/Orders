package ru.rarus.restart.orders.ui.order.add.callLog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.CallLogOperation;
import ru.rarus.restart.orders.ui.abscracts.BaseAdapter;


public class CallLogAdapter extends BaseAdapter<CallLogOperation, CallLogAdapter.ViewHolder> {

    public CallLogAdapter(OnItemClickListener<CallLogOperation> listener) {
        super(listener);
    }

    @Override
    protected ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calllog_adapter, parent, false);
        return new CallLogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CallLogOperation operation = getItemAt(position);
        if(operation.getName() == null ) {
            holder.tvCallName.setVisibility(View.GONE);
        } else {
            holder.tvCallName.setVisibility(View.VISIBLE);
            holder.tvCallName.setText(operation.getName());
        }
        holder.tvCallTel.setText(operation.getNumber());
        holder.tvCallDate.setText(new SimpleDateFormat("dd-MM HH:mm", Locale.ENGLISH).format(operation.getDate()));
        switch (Integer.parseInt(operation.getType())) {
            case 1: {
                holder.tvCallType.setText(R.string.cal_log_item_in_call);
                break;
            }
            case 2: {
                holder.tvCallType.setText(R.string.cal_log_item_out_call);
                break;
            }
            case 3: {
                holder.tvCallType.setText(R.string.cal_log_item_refused_call);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.call_icon)
        ImageView ivCallIcon;

        @BindView(R.id.call_name)
        TextView tvCallName;

        @BindView(R.id.call_tel)
        TextView tvCallTel;

        @BindView(R.id.call_date)
        TextView tvCallDate;

        @BindView(R.id.call_type)
        TextView tvCallType;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

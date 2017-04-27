package ru.rarus.restart.orders.ui.history;

import android.content.Context;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Operation;
import ru.rarus.restart.orders.ui.abscracts.BaseAdapter;
import ru.rarus.restart.orders.ui.util.NaturalDateFormat;
import ru.rarus.restart.orders.ui.util.RelativeDateFormat;

import static ru.rarus.restart.orders.data.info.InfoStorage.formatDateLight;


class HistoryAdapter extends BaseAdapter<Operation,HistoryAdapter.ViewHolder> {


    private Context context;

    public HistoryAdapter(OnItemClickListener<Operation> listener) {
        super(listener);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_operation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Operation operation = getItemAt(position);

        switch (operation.getAction()) {
            case Operation.Action.ACTION_SIGN_IN: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_operation_status_login));
                holder.tvTitle.setText(context.getResources().getString(R.string.action_login));
                break;
            }
            case Operation.Action.ACTION_SIGN_OUT: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_sing_out_selected));
                holder.tvTitle.setText(context.getResources().getString(R.string.action_login_out));

                break;
            }
            case Operation.Action.ACTION_ORDER_ATTACH: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_operation_status_add));
                holder.tvTitle.setText(context.getResources().getString(R.string.action_order_add));
                break;
            }
            case Operation.Action.ACTION_ORDER_DETACH: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_ic_detach_order));
                holder.tvTitle.setText(context.getResources().getString(R.string.text_order_detach));
                break;
            }
            case Operation.Action.ACTION_ORDER_SUCCESS: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_operation_status_success));
                String str = context.getResources().getString(R.string.action_order)
                        + " " + context.getResources().getString(R.string.action_order_success);
                holder.tvTitle.setText(str);
                break;
            }
            case Operation.Action.ACTION_ORDER_CANCELED: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_operation_status_cancel));
                holder.tvTitle.setText(context.getResources().getString(R.string.action_order_canceled));
                break;
            }
            case Operation.Action.ACTION_PUT_CASH: {
                holder.ivIcon.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, R.drawable.vector_drawable_cashbox_2));
                holder.tvTitle.setText(context.getResources().getString(R.string.text_put_cashbox));
                break;
            }
        }

        RelativeDateFormat relFormat = new RelativeDateFormat(context, NaturalDateFormat.DATE);
        relFormat.format(operation.getTime().getTime());
        holder.tvSubTitle.setText("");

        if(operation.getOrderId().isEmpty() && operation.getMessage().isEmpty()) holder.tvSubTitle.setVisibility(View.GONE);
        else holder.tvSubTitle.setVisibility(View.VISIBLE);

        if(!operation.getMessage().isEmpty()){
            holder.tvSubTitle.setText(operation.getMessage());
        }  else if (!operation.getOrderId().isEmpty()){
            holder.tvSubTitle.setText(operation.getOrderId());
        }

        holder.tvHistoryTime1.setText(formatDateLight.format(operation.getTime().getTime()));
        holder.tvHistoryTime2.setText(relFormat.format(operation.getTime().getTime()));
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_history_item_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_history_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_history_item_subtitle)
        TextView tvSubTitle;
        @BindView(R.id.tv_history_time_1)
        TextView tvHistoryTime1;
        @BindView(R.id.tv_history_time_2)
        TextView tvHistoryTime2;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

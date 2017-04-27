package ru.rarus.restart.orders.ui.abscracts;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;


public class BaseSimpleAdapter extends BaseAdapter<BaseSimpleItem, BaseSimpleAdapter.ViewHolder> {



    public BaseSimpleAdapter(OnItemClickListener<BaseSimpleItem> listener) {
        super(listener);
    }

    public List<BaseSimpleItem> getModelList() {
        return getList();
    }

    @Override
    protected ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_simple_adapter, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BaseSimpleItem item = getItemAt(position);

        holder.baseSimpleTvTitle.setText(item.getMainTitle());
        holder.baseSimpleTvRightTop.setText(item.getRightTopTitle());
        holder.baseSimpleTvTopLeft.setText(item.getTopLeftTitle());
        holder.baseSimpleTvSubTitle.setText(item.getSubTitle());
        holder.baseSimpleTvTopCenter.setText(String.valueOf(item.getTopCenterTitle()));
        holder.baseSimpleTvRightMedium.setText(String.valueOf(item.getRightMediumTitle()));
        holder.baseSimpleTvRightBottom.setText(String.valueOf(item.getRightBottomTitle()));

        if(item.getIcMain() != null)holder.icMain.setImageDrawable(item.getIcMain());

        if(item.getTextColorTopLeftTitle() != 0)holder.baseSimpleTvTopLeft.setTextColor(item.getTextColorTopLeftTitle());
        if(item.getTextColorRightTopTitle() != 0)holder.baseSimpleTvRightTop.setTextColor(item.getTextColorRightTopTitle());

    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icMain)
        ImageView icMain;
        @BindView(R.id.base_simple_tv_top_left)
        TextView baseSimpleTvTopLeft;
        @BindView(R.id.base_simple_tv_top_center)
        TextView baseSimpleTvTopCenter;
        @BindView(R.id.base_simple_tv_title)
        TextView baseSimpleTvTitle;
        @BindView(R.id.base_simple_tv_sub_title)
        TextView baseSimpleTvSubTitle;
        @BindView(R.id.base_simple_tv_right_top)
        TextView baseSimpleTvRightTop;
        @BindView(R.id.base_simple_tv_right_medium)
        TextView baseSimpleTvRightMedium;
        @BindView(R.id.base_simple_tv_right_bottom)
        TextView baseSimpleTvRightBottom;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

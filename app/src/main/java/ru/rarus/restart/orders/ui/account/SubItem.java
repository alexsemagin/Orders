package ru.rarus.restart.orders.ui.account;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.FlexibleViewHolder;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.ui.abscracts.UiUtils;
import ru.rarus.restart.orders.ui.orders.flexible.AbstractItem;

import static ru.rarus.restart.orders.ui.account.AccountPresenter.ORDERS_CLOSED;
import static ru.rarus.restart.orders.ui.account.AccountPresenter.ORDERS_IN_WORK;
import static ru.rarus.restart.orders.ui.orders.flexible.Utils.dpToPx;

/**
 * If you don't have many fields in common better to extend directly from
 * {@link eu.davidea.flexibleadapter.items.AbstractFlexibleItem} to benefit of the already
 * implemented methods (getter and setters).
 */
public class SubItem extends AbstractItem<SubItem.ChildViewHolder>
		implements ISectionable<SubItem.ChildViewHolder, IHeader>, IFilterable {

	/**
	 * The header of this item
	 */
	IHeader header;

	private int id;
	private int index;

	private String idItem;

	private String mainTitle;
	private Color textColorMainTitleId;

	private String subTitle;
	private int textColorSubTitleId;

	private String topLeftTitle;
	private int textColorTopLeftTitle;

	private String rightMediumTitle;
	private int textColorRightMediumTitle;

	private String rightBottomTitle;
	private int textColorRightBottomTitle;

	private String rightTopTitle;
	private int textColorRightTopTitle;

	private String topCenterTitle;
	private int textColorTopCenter;

	private Drawable icMain;


	public SubItem(String id) {
		super(id);
		setDraggable(false);
	}


	public static List<SubItem> getList(List<Order> listOrder, Context context, ExpandableHeaderItem expandableItem) {
		List<SubItem> list = new ArrayList<>();
		expandableItem.clearSubItems();

		for (Order c : listOrder) {
			SubItem bs = new SubItem(c.getId());
			//bs.setId(c.getId());
			bs.setIdItem(c.getId());

			bs.setSubTitle(c.getNumString());
			bs.setMainTitle(c.getOrderAddress() == null ? "" : c.getOrderAddress().getShortString());

			bs.setRightMediumTitle(InfoStorage.dfMoneyLight.format(c.getTotalSum()));
			bs.setRightBottomTitle(RestartSettings.sCurrency);
			bs.setRightTopTitle(InfoStorage.formatDateLight.format(c.getDateDelivery()));
			bs.setTextColorRightTopTitle(ContextCompat.getColor(context, R.color.accent));
			if (c.getLocation() == null) {
				bs.setTopCenterTitle("");
				bs.setIndex(0);
			}
			else{
				bs.setTopCenterTitle(c.getLocation().getStringLocation());
				bs.setIndex(c.getLocation().getIndex());
			}

			switch (c.getStatusPay()) {
				case Order.STATUS_PAY.NOT_PAY:
					bs.setTopLeftTitle(context.getResources().getString(R.string.text_order_pay_ok));
					bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.primary));
					bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_sum_no_pay));


					break;
				case Order.STATUS_PAY.PAID:
					bs.setTopLeftTitle(context.getResources().getString(R.string.text_pay_complete));
					bs.setTextColorTopLeftTitle(ContextCompat.getColor(context, R.color.accent));
					bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_sum_pay));


					break;
				default:
					bs.setTopLeftTitle(context.getResources().getString(R.string.text_no_info));
					break;
			}

			if (expandableItem.getIdHeader() == ORDERS_IN_WORK) bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_ic_motorcycle));
			if (expandableItem.getIdHeader() == ORDERS_CLOSED) bs.setIcMain(UiUtils.getDrawableRomRes(context, R.drawable.vector_drawable_complete_blue));

			bs.setHeader(expandableItem);
			expandableItem.addSubItem(bs);
			list.add(bs);

		}
		return list;

	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getIdItem() {
		return idItem;
	}

	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public Color getTextColorMainTitleId() {
		return textColorMainTitleId;
	}

	public void setTextColorMainTitleId(Color textColorMainTitleId) {
		this.textColorMainTitleId = textColorMainTitleId;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public int getTextColorSubTitleId() {
		return textColorSubTitleId;
	}

	public void setTextColorSubTitleId(int textColorSubTitleId) {
		this.textColorSubTitleId = textColorSubTitleId;
	}

	public String getTopLeftTitle() {
		return topLeftTitle;
	}

	public void setTopLeftTitle(String topLeftTitle) {
		this.topLeftTitle = topLeftTitle;
	}

	public int getTextColorTopLeftTitle() {
		return textColorTopLeftTitle;
	}

	public void setTextColorTopLeftTitle(int textColorTopLeftTitle) {
		this.textColorTopLeftTitle = textColorTopLeftTitle;
	}

	public String getRightMediumTitle() {
		return rightMediumTitle;
	}

	public void setRightMediumTitle(String rightMediumTitle) {
		this.rightMediumTitle = rightMediumTitle;
	}

	public int getTextColorRightMediumTitle() {
		return textColorRightMediumTitle;
	}

	public void setTextColorRightMediumTitle(int textColorRightMediumTitle) {
		this.textColorRightMediumTitle = textColorRightMediumTitle;
	}

	public String getRightBottomTitle() {
		return rightBottomTitle;
	}

	public void setRightBottomTitle(String rightBottomTitle) {
		this.rightBottomTitle = rightBottomTitle;
	}

	public int getTextColorRightBottomTitle() {
		return textColorRightBottomTitle;
	}

	public void setTextColorRightBottomTitle(int textColorRightBottomTitle) {
		this.textColorRightBottomTitle = textColorRightBottomTitle;
	}

	public String getRightTopTitle() {
		return rightTopTitle;
	}

	public void setRightTopTitle(String rightTopTitle) {
		this.rightTopTitle = rightTopTitle;
	}

	public int getTextColorRightTopTitle() {
		return textColorRightTopTitle;
	}

	public void setTextColorRightTopTitle(int textColorRightTopTitle) {
		this.textColorRightTopTitle = textColorRightTopTitle;
	}

	public String getTopCenterTitle() {
		return topCenterTitle;
	}

	public void setTopCenterTitle(String topCenterTitle) {
		this.topCenterTitle = topCenterTitle;
	}

	public int getTextColorTopCenter() {
		return textColorTopCenter;
	}

	public void setTextColorTopCenter(int textColorTopCenter) {
		this.textColorTopCenter = textColorTopCenter;
	}

	public Drawable getIcMain() {
		return icMain;
	}

	public void setIcMain(Drawable icMain) {
		this.icMain = icMain;
	}

	@Override
	public IHeader getHeader() {
		return header;
	}

	@Override
	public void setHeader(IHeader header) {
		this.header = header;
	}

	@Override
	public int getLayoutRes() {
		return R.layout.item_base_simple_adapter_account;
	}

	@Override
	public ChildViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
		return new ChildViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void bindViewHolder(FlexibleAdapter adapter, ChildViewHolder holder, int position, List payloads) {
		//In case of searchText matches with Title or with an SimpleItem's field
		// this will be highlighted
//		if (adapter.hasSearchText()) {
//			Context context = holder.itemView.getContext();
//			Utils.highlightText(context, holder.mTitle, getTitle(), adapter.getSearchText(),
//					context.getResources().getColor(R.color.accent));
//		} else {
//			holder.baseSimpleTvTitle.setText(getTitle());
//		}

//		if (getHeader() != null) {
//			setSubtitle("Header " + getHeader().toString());
//		}

		SubItem item = (SubItem) adapter.getItem(position);

		holder.drag.setVisibility(View.GONE);
		holder.drag_padding.setVisibility(View.VISIBLE);
		holder.baseSimpleTvTitle.setText(item.getMainTitle());
		holder.baseSimpleTvRightTop.setText(item.getRightTopTitle());
		holder.baseSimpleTvTopLeft.setText(item.getTopLeftTitle());

		holder.baseSimpleTvSubTitle.setText(item.getSubTitle());
		holder.baseSimpleTvTopCenter.setText(String.valueOf(item.getTopCenterTitle()));
		holder.baseSimpleTvRightMedium.setText(String.valueOf(item.getRightMediumTitle()));
		holder.baseSimpleTvRightBottom.setText(String.valueOf(item.getRightBottomTitle()));

		if (item.getIcMain() != null) holder.icMain.setImageDrawable(item.getIcMain());

		if (item.getTextColorTopLeftTitle() != 0)
			holder.baseSimpleTvTopLeft.setTextColor(item.getTextColorTopLeftTitle());
		if (item.getTextColorRightTopTitle() != 0)
			holder.baseSimpleTvRightTop.setTextColor(item.getTextColorRightTopTitle());
	}

	@Override
	public boolean filter(String constraint) {
		return getTitle() != null && getTitle().toLowerCase().trim().contains(constraint);
	}

	/**
	 * Provide a reference to the views for each data item.
	 * Complex data labels may need more than one view per item, and
	 * you provide access to all the views for a data item in a view holder.
	 */
	static final class ChildViewHolder extends FlexibleViewHolder {

		public TextView mTitle;
		public TextView mSubtitle;
		public ImageView mIcon;


		@BindView(R.id.icMain)
		public ImageView icMain;
		@BindView(R.id.base_simple_tv_top_left)
		public TextView baseSimpleTvTopLeft;
		@BindView(R.id.base_simple_tv_top_center)
		public TextView baseSimpleTvTopCenter;
		@BindView(R.id.base_simple_tv_title)
		public TextView baseSimpleTvTitle;
		@BindView(R.id.base_simple_tv_sub_title)
		public TextView baseSimpleTvSubTitle;
		@BindView(R.id.base_simple_tv_right_top)
		public TextView baseSimpleTvRightTop;
		@BindView(R.id.base_simple_tv_right_medium)
		public TextView baseSimpleTvRightMedium;
		@BindView(R.id.base_simple_tv_right_bottom)
		public TextView baseSimpleTvRightBottom;
		@BindView(R.id.drag)
		public FrameLayout drag;
		@BindView(R.id.drag_padding)
		public View drag_padding;

		public ChildViewHolder(View view, FlexibleAdapter adapter) {
			super(view, adapter);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public float getActivationElevation() {
			return dpToPx(itemView.getContext(), 4f);
		}

		@Override
		public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
			AnimatorHelper.scaleAnimator(animators, itemView, 0f);
		}
	}

	@Override
	public String toString() {
		return "SubItem[" + super.toString() + "]";
	}

}
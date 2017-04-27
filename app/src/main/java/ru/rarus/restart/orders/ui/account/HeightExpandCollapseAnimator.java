package ru.rarus.restart.orders.ui.account;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class HeightExpandCollapseAnimator {

    public static void set(Context context, View expander, FrameLayout target, View item,
            int duration, IExpanderClickHandler handler) {

        final int MAX_HEIGHT = 10000; // надо чем то заменить

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(expander.getWidth(), View
                .MeasureSpec.AT_MOST);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(MAX_HEIGHT,
                View.MeasureSpec.AT_MOST);

        target.measure(widthSpec, heightSpec);

        int valueFrom = target.getMeasuredHeight();

        target.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        target.addView(item);

        target.measure(widthSpec, heightSpec);

        int valueTo = target.getMeasuredHeight();

        target.removeAllViews();

        target.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                valueFrom));

        Log.d("ExpandAnimator", "from " + valueFrom + " to " + valueTo);

        ObjectAnimator expandAnimator = ObjectAnimator.ofInt(target
                        .getLayoutParams().height,
                "layout_height", valueFrom, valueTo);

        expandAnimator.addUpdateListener(valueAnimator -> {

            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
            layoutParams.height = val;
            target.setLayoutParams(layoutParams);

        });

        ObjectAnimator collapseAnimator = ObjectAnimator.ofInt(target
                        .getLayoutParams().height,
                "layout_height", valueTo, valueFrom);

        collapseAnimator.addUpdateListener(valueAnimator -> {

            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
            layoutParams.height = val;
            target.setLayoutParams(layoutParams);

        });

        expandAnimator.setDuration(duration);

        expander.setOnClickListener(v -> {

            boolean isExpanded = target.getChildCount() == 0;

            handler.onExpanderClick(v, isExpanded);

            if (isExpanded) {

                target.addView(item);
                expandAnimator.start();

            } else {

                target.removeAllViews();
                collapseAnimator.start();

            }

        });

    }

    public interface IExpanderClickHandler {
        void onExpanderClick(View v, boolean isExpanded);
    }

}

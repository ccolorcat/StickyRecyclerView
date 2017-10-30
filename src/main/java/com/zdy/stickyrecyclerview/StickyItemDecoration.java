package com.zdy.stickyrecyclerview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with Android Studio.
 * Time: 18:06  2017/10/27
 * Author:ZhuangYuan
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    private TitleListener titleListener;
    private int mGroupHeight = 120;
    private int mDivideHeight = 1;
    private Paint mDividePaint;
    private int mDividerColor = Color.parseColor("#CCCCCC");


    public StickyItemDecoration() {
        mDividePaint = new Paint();
    }

    public interface TitleListener {
        String getTitleContent(int position);

        View getTitleView(int position);
    }

    public void setTitleListener(TitleListener titleListener) {
        this.titleListener = titleListener;
        mDividePaint.setColor(mDividerColor);
    }

    //判断是不是组中的第一个位置
    // 根据前一个组名，判断当前是否为新的组
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String preProvinceName = titleListener.getTitleContent(pos - 1);
            String currentProvinceName = titleListener.getTitleContent(pos);
            return !TextUtils.equals(preProvinceName, currentProvinceName);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String currentProvinceName = titleListener.getTitleContent(pos);
        if (currentProvinceName == null) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mGroupHeight;
        } else {
            outRect.top = mDivideHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        String preGroupName;
        String currentGroupName = null;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = titleListener.getTitleContent(position);
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName)) {
                //绘制分割线
                if (mDivideHeight != 0) {
                    float bottom = view.getTop();
                    if (bottom < mGroupHeight) {
                        //高度小于顶部悬浮栏时，跳过绘制
                        continue;
                    }
                    c.drawRect(left, bottom - mDivideHeight, right, bottom, mDividePaint);
                }
            } else {
                int viewBottom = view.getBottom();
                int top = Math.max(mGroupHeight, view.getTop());//top 决定当前顶部第一个悬浮Group的位置
                if (position + 1 < itemCount) {
                    //获取下个GroupName
                    String nextGroupName = titleListener.getTitleContent(position + 1);
                    //下一组的第一个View接近头部
                    if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                        top = viewBottom;
                    }
                }
                //根据position获取View
                View groupView = titleListener.getTitleView(position);
                if (groupView == null) return;
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(right, mGroupHeight);
                groupView.setLayoutParams(layoutParams);
                groupView.setDrawingCacheEnabled(true);
                groupView.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                //指定高度、宽度的groupView
                groupView.layout(0, 0, right, mGroupHeight);
                groupView.buildDrawingCache();
                Bitmap bitmap = groupView.getDrawingCache();
                int marginLeft = 0;
                c.drawBitmap(bitmap, left + marginLeft, top - mGroupHeight, null);
            }
        }
    }


    public static class Builder {
        StickyItemDecoration mDecoration;

        private Builder(TitleListener listener) {
            mDecoration = new StickyItemDecoration();
            mDecoration.setTitleListener(listener);
        }

        public static Builder init(TitleListener listener) {
            return new Builder(listener);
        }

        /**
         * 设置Group高度
         */
        public Builder setGroupHeight(int groutHeight) {
            mDecoration.mGroupHeight = groutHeight;
            return this;
        }


        /**
         * 设置分割线高度
         */
        public Builder setDivideHeight(int height) {
            mDecoration.mDivideHeight = height;
            return this;
        }

        /**
         * 设置分割线颜色
         */
        public Builder setDivideColor(@ColorInt int color) {
            mDecoration.mDividePaint.setColor(color);
            return this;
        }

        public StickyItemDecoration build() {
            return mDecoration;
        }
    }

}
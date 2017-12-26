package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by shucc on 17/12/15.
 * cc@cchao.org
 */
public abstract class ILoadingLayout extends FrameLayout {

    public ILoadingLayout(@NonNull Context context) {
        super(context);
    }

    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    abstract public int getContentSize();

    abstract public void pullToRefresh();

    /**
     * 刷新/加载中
     */
    abstract public void refreshing();

    abstract public void releaseToRefresh();

    /**
     * 刷新/加载结束
     */
    abstract public void reset();

    /**
     * 下拉/上拉过程中
     * @param scaleOfLayout
     */
    abstract public void onPull(float scaleOfLayout);
}

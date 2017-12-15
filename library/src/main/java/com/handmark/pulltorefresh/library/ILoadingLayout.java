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

    abstract public void refreshing();

    abstract public void releaseToRefresh();

    abstract public void reset();

    abstract public void onPull(float scaleOfLayout);
}

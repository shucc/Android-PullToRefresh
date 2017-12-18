package org.cchao.pullrefreshdemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.handmark.pulltorefresh.library.ILoadingLayout;

import org.cchao.pullrefreshdemo.R;

/**
 * Created by shucc on 17/12/18.
 * cc@cchao.org
 */
public class CustomLoadingLayout extends ILoadingLayout {

    private FrameLayout flParent;

    public CustomLoadingLayout(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_custom_loading, this);
        flParent = findViewById(R.id.fl_parent);
    }

    @Override
    public int getContentSize() {
        return flParent.getHeight();
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshing() {

    }

    @Override
    public void releaseToRefresh() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void onPull(float scaleOfLayout) {

    }
}

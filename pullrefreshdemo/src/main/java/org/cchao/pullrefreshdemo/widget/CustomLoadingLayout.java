package org.cchao.pullrefreshdemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.handmark.pulltorefresh.library.ILoadingLayout;

import org.cchao.pullrefreshdemo.R;

/**
 * Created by shucc on 17/12/18.
 * cc@cchao.org
 */
public class CustomLoadingLayout extends ILoadingLayout {

    private final String TAG = getClass().getName();

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
        Log.d(TAG, "pullToRefresh: ");
    }

    @Override
    public void refreshing() {
        Log.d(TAG, "refreshing: ");
    }

    @Override
    public void releaseToRefresh() {
        Log.d(TAG, "releaseToRefresh: ");
    }

    @Override
    public void reset() {
        Log.d(TAG, "reset: ");
    }

    @Override
    public void onPull(float scaleOfLayout) {
        Log.d(TAG, "onPull: " + scaleOfLayout);
    }
}

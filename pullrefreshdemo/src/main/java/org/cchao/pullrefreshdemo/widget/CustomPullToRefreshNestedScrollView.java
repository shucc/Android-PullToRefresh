package org.cchao.pullrefreshdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshNestedScrollView;

/**
 * Created by shucc on 17/12/18.
 * cc@cchao.org
 */
public class CustomPullToRefreshNestedScrollView extends PullToRefreshNestedScrollView {

    public CustomPullToRefreshNestedScrollView(Context context) {
        super(context);
    }

    public CustomPullToRefreshNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPullToRefreshNestedScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    public ILoadingLayout createFooterLoadingLayout(Context context, TypedArray attrs) {
        return new CustomLoadingLayout(context);
    }

    @Override
    public ILoadingLayout createHeaderLoadingLayout(Context context, TypedArray attrs) {
        return super.createHeaderLoadingLayout(context, attrs);
    }
}
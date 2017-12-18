/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

public class PullToRefreshNestedScrollView extends PullToRefreshBase<NestedScrollView> {

    public PullToRefreshNestedScrollView(Context context) {
        super(context);
    }

    public PullToRefreshNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshNestedScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    protected NestedScrollView createRefreshableView(Context context, AttributeSet attrs) {
        NestedScrollView scrollView;
        scrollView = new NestedScrollView(context, attrs);
        scrollView.setId(R.id.default_pull_to_scroll_view);
        return scrollView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        return null != scrollViewChild && mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
    }
}

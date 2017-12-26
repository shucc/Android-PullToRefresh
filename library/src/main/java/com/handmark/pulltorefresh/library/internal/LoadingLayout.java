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
package com.handmark.pulltorefresh.library.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("ViewConstructor")
public class LoadingLayout extends ILoadingLayout {

    private final String TAG = "LoadingLayout";

    private final static String KEY_LAST_UPDATE_TIME_SP = "ptr_last_update";
    private final static String KEY_LAST_UPDATE_TIME = "last_update_time";

    private SimpleDateFormat mDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private RelativeLayout mInnerLayout;
    private ImageView mHeaderImage;
    private ProgressBar mHeaderProgress;
    private TextView mHeaderText;
    private TextView mSubHeaderText;

    private boolean mUseIntrinsicAnimation;

    private final Mode mMode;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;
    private CharSequence mRefreshComplete;

    private boolean showHeaderSub = false;

    private long mLastUpdateTime = -1;

    private final Animation mRotateAnimation;
    private final Animation mResetRotateAnimation;

    public LoadingLayout(Context context, final Mode mode, TypedArray attrs) {
        super(context);
        mMode = mode;
        LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical, this);

        mInnerLayout = findViewById(R.id.rl_inner);
        mHeaderText = mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        mHeaderProgress = mInnerLayout.findViewById(R.id.pull_to_refresh_progress);
        mSubHeaderText = mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        mHeaderImage = mInnerLayout.findViewById(R.id.pull_to_refresh_image);

        final int rotateAngle = mode == Mode.PULL_FROM_START ? -180 : 180;
        mRotateAnimation = new RotateAnimation(0, rotateAngle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(150);
        mRotateAnimation.setFillAfter(true);

        mResetRotateAnimation = new RotateAnimation(rotateAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mResetRotateAnimation.setInterpolator(new LinearInterpolator());
        mResetRotateAnimation.setDuration(150);
        mResetRotateAnimation.setFillAfter(true);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInnerLayout.getLayoutParams();

        switch (mode) {
            case PULL_FROM_END:
                lp.gravity = Gravity.TOP;
                mPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                mRefreshingLabel = context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
                mReleaseLabel = context.getString(R.string.pull_to_refresh_from_bottom_release_label);
                mRefreshComplete = context.getString(R.string.pull_to_refresh_from_bottom_refresh_complete);
                break;
            case PULL_FROM_START:
                lp.gravity = Gravity.BOTTOM;
                mPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                mRefreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
                mReleaseLabel = context.getString(R.string.pull_to_refresh_release_label);
                mRefreshComplete = context.getString(R.string.pull_to_refresh_refresh_complete);
                break;
            default:
                break;
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderBackground)) {
            Drawable background = attrs.getDrawable(R.styleable.PullToRefresh_ptrHeaderBackground);
            if (null != background) {
                setBackground(background);
            }
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextAppearance)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(R.styleable.PullToRefresh_ptrHeaderTextAppearance, styleID);
            setTextAppearance(styleID.data);
        }
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrSubHeaderTextAppearance)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(R.styleable.PullToRefresh_ptrSubHeaderTextAppearance, styleID);
            setSubTextAppearance(styleID.data);
        }

        // Text Color attrs need to be set after TextAppearance attrs
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextColor)) {
            ColorStateList colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderTextColor);
            if (null != colors) {
                setTextColor(colors);
            }
        }
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrLastUpdateTimeTextColor)) {
            ColorStateList colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrLastUpdateTimeTextColor);
            if (null != colors) {
                setSubTextColor(colors);
            }
        }
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrShowLastUpdateTime)) {
            showHeaderSub = attrs.getBoolean(R.styleable.PullToRefresh_ptrShowLastUpdateTime, false);
        }

        // Try and get defined drawable from Attrs
        Drawable imageDrawable = null;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawable)) {
            imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawable);
        }

        // If we don't have a user defined drawable, load the default
        if (null == imageDrawable) {
            imageDrawable = context.getResources().getDrawable(getDefaultDrawableResId());
        }

        // Set Drawable, and save width/height
        setLoadingDrawable(imageDrawable);

        reset();
    }

    @Override
    public int getContentSize() {
        return mInnerLayout.getHeight();
    }

    @Override
    public final void onPull(float scaleOfLayout) {
        if (!mUseIntrinsicAnimation) {

        }
    }

    @Override
    public final void pullToRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText(mPullLabel);
        }
        if (showHeaderSub && mMode == Mode.PULL_FROM_START && null != mSubHeaderText) {
            String time = getLastUpdateTime();
            if (!TextUtils.isEmpty(time)) {
                mSubHeaderText.setVisibility(VISIBLE);
                mSubHeaderText.setText(getLastUpdateTime());
            } else {
                mSubHeaderText.setVisibility(GONE);
            }
        }
        if (mRotateAnimation == mHeaderImage.getAnimation()) {
            mHeaderImage.startAnimation(mResetRotateAnimation);
        }
    }

    @Override
    public final void refreshing() {
        if (null != mHeaderText) {
            mHeaderText.setText(mRefreshingLabel);
        }

        if (mUseIntrinsicAnimation) {
            ((AnimationDrawable) mHeaderImage.getDrawable()).start();
        } else {
            // Now call the callback
            mHeaderImage.clearAnimation();
            mHeaderImage.setVisibility(View.INVISIBLE);
            mHeaderProgress.setVisibility(View.VISIBLE);
        }

        if (null != mSubHeaderText) {
            mSubHeaderText.setVisibility(View.GONE);
        }
        if (mMode == Mode.PULL_FROM_START) {
            // update last update time
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_LAST_UPDATE_TIME_SP, 0);
            mLastUpdateTime = new Date().getTime();
            sharedPreferences.edit().putLong(KEY_LAST_UPDATE_TIME, mLastUpdateTime).apply();
        }
    }

    @Override
    public final void releaseToRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText(mReleaseLabel);
        }
        // Now call the callback
        mHeaderImage.startAnimation(mRotateAnimation);
    }

    @Override
    public final void reset() {
        if (null != mHeaderText) {
            mHeaderText.setText(mRefreshComplete);
        }
        //mHeaderImage.setVisibility(View.VISIBLE);
        if (mUseIntrinsicAnimation) {
            ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        } else {
            mHeaderImage.clearAnimation();
            mHeaderProgress.setVisibility(View.GONE);
            mHeaderImage.setVisibility(View.VISIBLE);
        }
    }

    private String getLastUpdateTime() {
        if (mLastUpdateTime == -1) {
            mLastUpdateTime = getContext().getSharedPreferences(KEY_LAST_UPDATE_TIME_SP, 0).getLong(KEY_LAST_UPDATE_TIME, -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.pull_to_refresh_last_update));
        sb.append(mDataFormat.format(new Date(mLastUpdateTime)));
        return sb.toString();
    }

    public final void setLoadingDrawable(Drawable imageDrawable) {
        // Set Drawable
        mHeaderImage.setImageDrawable(imageDrawable);
        mUseIntrinsicAnimation = (imageDrawable instanceof AnimationDrawable);

        // Now call the callback
        onLoadingDrawableSet(imageDrawable);
    }


    /**
     * Callbacks for derivative Layouts
     */

    protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_flip;
    }

    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            final int dHeight = imageDrawable.getIntrinsicHeight();
            final int dWidth = imageDrawable.getIntrinsicWidth();

            /**
             * We need to set the width/height of the ImageView so that it is
             * square with each side the size of the largest drawable dimension.
             * This is so that it doesn't clip when rotated.
             */
            ViewGroup.LayoutParams lp = mHeaderImage.getLayoutParams();
            lp.width = lp.height = Math.max(dHeight, dWidth);
            mHeaderImage.requestLayout();

            /**
             * We now rotate the Drawable so that is at the correct rotation,
             * and is centered.
             */
            mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.postTranslate((lp.width - dWidth) / 2f, (lp.height - dHeight) / 2f);
            matrix.postRotate(getDrawableRotationAngle(), lp.width / 2f, lp.height / 2f);
            mHeaderImage.setImageMatrix(matrix);
        }
    }

    private float getDrawableRotationAngle() {
        float angle = 0f;
        switch (mMode) {
            case PULL_FROM_END:
                angle = 180f;
                break;
            case PULL_FROM_START:
                break;
            default:
                break;
        }
        return angle;
    }

    private void setSubTextAppearance(int value) {
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setSubTextColor(ColorStateList color) {
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextColor(color);
        }
    }

    private void setTextAppearance(int value) {
        if (null != mHeaderText) {
            mHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setTextColor(ColorStateList color) {
        if (null != mHeaderText) {
            mHeaderText.setTextColor(color);
        }
    }
}

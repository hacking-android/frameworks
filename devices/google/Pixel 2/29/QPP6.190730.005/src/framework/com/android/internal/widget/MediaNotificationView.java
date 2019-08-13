/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class MediaNotificationView
extends FrameLayout {
    private View mActions;
    private NotificationHeaderView mHeader;
    private int mImagePushIn;
    private View mMainColumn;
    private View mMediaContent;
    private final int mNotificationContentImageMarginEnd;
    private final int mNotificationContentMarginEnd;
    private ImageView mRightIcon;

    public MediaNotificationView(Context context) {
        this(context, null);
    }

    public MediaNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MediaNotificationView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MediaNotificationView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mNotificationContentMarginEnd = context.getResources().getDimensionPixelSize(17105307);
        this.mNotificationContentImageMarginEnd = context.getResources().getDimensionPixelSize(17105305);
    }

    private void resetHeaderIndention() {
        Object object;
        if (this.mHeader.getPaddingEnd() != this.mNotificationContentMarginEnd) {
            object = this.mHeader;
            ((View)object).setPaddingRelative(((View)object).getPaddingStart(), this.mHeader.getPaddingTop(), this.mNotificationContentMarginEnd, this.mHeader.getPaddingBottom());
        }
        object = (ViewGroup.MarginLayoutParams)this.mHeader.getLayoutParams();
        ((ViewGroup.MarginLayoutParams)object).setMarginEnd(0);
        if (((ViewGroup.MarginLayoutParams)object).getMarginEnd() != 0) {
            ((ViewGroup.MarginLayoutParams)object).setMarginEnd(0);
            this.mHeader.setLayoutParams((ViewGroup.LayoutParams)object);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mRightIcon = (ImageView)this.findViewById(16909294);
        this.mActions = this.findViewById(16909096);
        this.mHeader = (NotificationHeaderView)this.findViewById(16909164);
        this.mMainColumn = this.findViewById(16909165);
        this.mMediaContent = this.findViewById(16909172);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mImagePushIn > 0) {
            if (this.getLayoutDirection() == 1) {
                this.mImagePushIn *= -1;
            }
            ImageView imageView = this.mRightIcon;
            imageView.layout(imageView.getLeft() + this.mImagePushIn, this.mRightIcon.getTop(), this.mRightIcon.getRight() + this.mImagePushIn, this.mRightIcon.getBottom());
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = this.mRightIcon.getVisibility() != 8 ? 1 : 0;
        if (n3 == 0) {
            this.resetHeaderIndention();
        }
        super.onMeasure(n, n2);
        int n4 = View.MeasureSpec.getMode(n);
        int n5 = 0;
        int n6 = 0;
        this.mImagePushIn = 0;
        if (n3 != 0 && n4 != 0) {
            n3 = View.MeasureSpec.getSize(n);
            n5 = this.mActions.getMeasuredWidth();
            Object object = (ViewGroup.MarginLayoutParams)this.mRightIcon.getLayoutParams();
            int n7 = ((ViewGroup.MarginLayoutParams)object).getMarginEnd();
            n4 = n3 - n5 - n7;
            n5 = this.mMediaContent.getMeasuredHeight();
            if (n4 > n5) {
                n3 = n5;
            } else {
                n3 = n4;
                if (n4 < n5) {
                    n3 = Math.max(0, n4);
                    this.mImagePushIn = n5 - n3;
                }
            }
            if (((ViewGroup.MarginLayoutParams)object).width != n5 || ((ViewGroup.MarginLayoutParams)object).height != n5) {
                ((ViewGroup.MarginLayoutParams)object).width = n5;
                ((ViewGroup.MarginLayoutParams)object).height = n5;
                this.mRightIcon.setLayoutParams((ViewGroup.LayoutParams)object);
                n6 = 1;
            }
            if ((n5 = n3 + n7 + this.mNotificationContentMarginEnd) != ((ViewGroup.MarginLayoutParams)(object = (ViewGroup.MarginLayoutParams)this.mMainColumn.getLayoutParams())).getMarginEnd()) {
                ((ViewGroup.MarginLayoutParams)object).setMarginEnd(n5);
                this.mMainColumn.setLayoutParams((ViewGroup.LayoutParams)object);
                n6 = 1;
            }
            if ((n3 += n7) != this.mHeader.getHeaderTextMarginEnd()) {
                this.mHeader.setHeaderTextMarginEnd(n3);
                n6 = 1;
            }
            if (((ViewGroup.MarginLayoutParams)(object = (ViewGroup.MarginLayoutParams)this.mHeader.getLayoutParams())).getMarginEnd() != n7) {
                ((ViewGroup.MarginLayoutParams)object).setMarginEnd(n7);
                this.mHeader.setLayoutParams((ViewGroup.LayoutParams)object);
                n6 = 1;
            }
            if (this.mHeader.getPaddingEnd() != this.mNotificationContentImageMarginEnd) {
                object = this.mHeader;
                ((View)object).setPaddingRelative(((View)object).getPaddingStart(), this.mHeader.getPaddingTop(), this.mNotificationContentImageMarginEnd, this.mHeader.getPaddingBottom());
                n6 = 1;
            }
        } else {
            n6 = n5;
        }
        if (n6 != 0) {
            super.onMeasure(n, n2);
        }
    }
}


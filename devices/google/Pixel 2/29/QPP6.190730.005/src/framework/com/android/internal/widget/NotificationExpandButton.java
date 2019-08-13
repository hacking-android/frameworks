/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class NotificationExpandButton
extends ImageView {
    public NotificationExpandButton(Context context) {
        super(context);
    }

    public NotificationExpandButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NotificationExpandButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public NotificationExpandButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private void extendRectToMinTouchSize(Rect rect) {
        int n = (int)(this.getResources().getDisplayMetrics().density * 48.0f);
        rect.left = rect.centerX() - n / 2;
        rect.right = rect.left + n;
        rect.top = rect.centerY() - n / 2;
        rect.bottom = rect.top + n;
    }

    @Override
    public void getBoundsOnScreen(Rect rect, boolean bl) {
        super.getBoundsOnScreen(rect, bl);
        this.extendRectToMinTouchSize(rect);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.view.View;

public interface LockScreenWidgetCallback {
    public boolean isVisible(View var1);

    public void requestHide(View var1);

    public void requestShow(View var1);

    public void userActivity(View var1);
}


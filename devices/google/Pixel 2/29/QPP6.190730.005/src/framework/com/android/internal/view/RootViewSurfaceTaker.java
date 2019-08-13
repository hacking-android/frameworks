/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.view.InputQueue;
import android.view.SurfaceHolder;

public interface RootViewSurfaceTaker {
    public void onRootViewScrollYChanged(int var1);

    public void setSurfaceFormat(int var1);

    public void setSurfaceKeepScreenOn(boolean var1);

    public void setSurfaceType(int var1);

    public InputQueue.Callback willYouTakeTheInputQueue();

    public SurfaceHolder.Callback2 willYouTakeTheSurface();
}


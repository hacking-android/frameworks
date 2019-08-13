/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.View;
import android.view.ViewGroup;

public interface ViewManager {
    public void addView(View var1, ViewGroup.LayoutParams var2);

    public void removeView(View var1);

    public void updateViewLayout(View var1, ViewGroup.LayoutParams var2);
}


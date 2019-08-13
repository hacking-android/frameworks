/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget.helper;

import android.graphics.Canvas;
import android.view.View;
import com.android.internal.widget.RecyclerView;

public interface ItemTouchUIUtil {
    public void clearView(View var1);

    public void onDraw(Canvas var1, RecyclerView var2, View var3, float var4, float var5, int var6, boolean var7);

    public void onDrawOver(Canvas var1, RecyclerView var2, View var3, float var4, float var5, int var6, boolean var7);

    public void onSelected(View var1);
}


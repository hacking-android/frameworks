/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget.helper;

import android.graphics.Canvas;
import android.view.View;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.helper.ItemTouchUIUtil;

class ItemTouchUIUtilImpl
implements ItemTouchUIUtil {
    ItemTouchUIUtilImpl() {
    }

    private float findMaxElevation(RecyclerView recyclerView, View view) {
        int n = recyclerView.getChildCount();
        float f = 0.0f;
        for (int i = 0; i < n; ++i) {
            float f2;
            View view2 = recyclerView.getChildAt(i);
            if (view2 == view) {
                f2 = f;
            } else {
                float f3 = view2.getElevation();
                f2 = f;
                if (f3 > f) {
                    f2 = f3;
                }
            }
            f = f2;
        }
        return f;
    }

    @Override
    public void clearView(View view) {
        Object object = view.getTag(16909045);
        if (object != null && object instanceof Float) {
            view.setElevation(((Float)object).floatValue());
        }
        view.setTag(16909045, null);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int n, boolean bl) {
        if (bl && view.getTag(16909045) == null) {
            float f3 = view.getElevation();
            view.setElevation(this.findMaxElevation(recyclerView, view) + 1.0f);
            view.setTag(16909045, Float.valueOf(f3));
        }
        view.setTranslationX(f);
        view.setTranslationY(f2);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int n, boolean bl) {
    }

    @Override
    public void onSelected(View view) {
    }
}


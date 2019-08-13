/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.view.View;
import android.view.ViewOverlay;

public class ViewGroupOverlay
extends ViewOverlay {
    ViewGroupOverlay(Context context, View view) {
        super(context, view);
    }

    public void add(View view) {
        this.mOverlayViewGroup.add(view);
    }

    public void remove(View view) {
        this.mOverlayViewGroup.remove(view);
    }
}


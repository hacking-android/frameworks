/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.graphics.Rect;
import android.view.View;

public interface TransformationMethod {
    public CharSequence getTransformation(CharSequence var1, View var2);

    public void onFocusChanged(View var1, CharSequence var2, boolean var3, int var4, Rect var5);
}


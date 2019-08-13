/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.res.Resources;
import android.widget.SpinnerAdapter;

public interface ThemedSpinnerAdapter
extends SpinnerAdapter {
    public Resources.Theme getDropDownViewTheme();

    public void setDropDownViewTheme(Resources.Theme var1);
}


/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.NoCopySpan;
import android.text.Spannable;

public interface SpanWatcher
extends NoCopySpan {
    public void onSpanAdded(Spannable var1, Object var2, int var3, int var4);

    public void onSpanChanged(Spannable var1, Object var2, int var3, int var4, int var5, int var6);

    public void onSpanRemoved(Spannable var1, Object var2, int var3, int var4);
}


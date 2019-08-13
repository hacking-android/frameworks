/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.KeyEvent;
import android.view.View;

public interface FallbackEventHandler {
    public boolean dispatchKeyEvent(KeyEvent var1);

    public void preDispatchKeyEvent(KeyEvent var1);

    public void setView(View var1);
}


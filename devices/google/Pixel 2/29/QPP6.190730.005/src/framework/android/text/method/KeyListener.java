/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

public interface KeyListener {
    public void clearMetaKeyState(View var1, Editable var2, int var3);

    public int getInputType();

    public boolean onKeyDown(View var1, Editable var2, int var3, KeyEvent var4);

    public boolean onKeyOther(View var1, Editable var2, KeyEvent var3);

    public boolean onKeyUp(View var1, Editable var2, int var3, KeyEvent var4);
}


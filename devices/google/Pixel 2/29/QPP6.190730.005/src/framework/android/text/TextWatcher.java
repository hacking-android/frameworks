/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.Editable;
import android.text.NoCopySpan;

public interface TextWatcher
extends NoCopySpan {
    public void afterTextChanged(Editable var1);

    public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4);

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4);
}


/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.Context;
import android.content.ContextWrapper;

public class MutableContextWrapper
extends ContextWrapper {
    public MutableContextWrapper(Context context) {
        super(context);
    }

    public void setBaseContext(Context context) {
        this.mBase = context;
    }
}


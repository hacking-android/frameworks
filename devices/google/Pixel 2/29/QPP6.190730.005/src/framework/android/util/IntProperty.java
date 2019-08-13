/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Property;

public abstract class IntProperty<T>
extends Property<T, Integer> {
    public IntProperty(String string2) {
        super(Integer.class, string2);
    }

    @Override
    public final void set(T t, Integer n) {
        this.setValue(t, n);
    }

    public abstract void setValue(T var1, int var2);
}


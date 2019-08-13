/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Property;

public abstract class FloatProperty<T>
extends Property<T, Float> {
    public FloatProperty(String string2) {
        super(Float.class, string2);
    }

    @Override
    public final void set(T t, Float f) {
        this.setValue(t, f.floatValue());
    }

    public abstract void setValue(T var1, float var2);
}


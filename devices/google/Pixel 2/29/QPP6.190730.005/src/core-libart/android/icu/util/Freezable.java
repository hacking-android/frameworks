/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

public interface Freezable<T>
extends Cloneable {
    public T cloneAsThawed();

    public T freeze();

    public boolean isFrozen();
}


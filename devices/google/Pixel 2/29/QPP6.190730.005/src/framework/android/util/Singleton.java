/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;

public abstract class Singleton<T> {
    @UnsupportedAppUsage
    private T mInstance;

    protected abstract T create();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public final T get() {
        synchronized (this) {
            if (this.mInstance == null) {
                this.mInstance = this.create();
            }
            T t = this.mInstance;
            return t;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

public final class ArgumentPlaceholder<R> {
    static final ArgumentPlaceholder<?> INSTANCE = new ArgumentPlaceholder<R>();

    private ArgumentPlaceholder() {
    }

    public String toString() {
        return "_";
    }
}


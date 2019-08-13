/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.ConnectionSpec;

public class ConnectionSpecs {
    private ConnectionSpecs() {
    }

    public static ConnectionSpec.Builder builder(boolean bl) {
        return new ConnectionSpec.Builder(bl);
    }
}


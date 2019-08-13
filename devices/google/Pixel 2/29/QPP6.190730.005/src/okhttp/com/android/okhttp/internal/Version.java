/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal;

import dalvik.annotation.compat.UnsupportedAppUsage;

public final class Version {
    private Version() {
    }

    @UnsupportedAppUsage
    public static String userAgent() {
        CharSequence charSequence = System.getProperty("http.agent");
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Java");
            ((StringBuilder)charSequence).append(System.getProperty("java.version"));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }
}


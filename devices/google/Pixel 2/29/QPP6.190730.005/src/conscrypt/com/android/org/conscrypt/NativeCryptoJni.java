/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

class NativeCryptoJni {
    private NativeCryptoJni() {
    }

    public static void init() {
        System.loadLibrary("javacrypto");
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

public class FuseUnavailableMountException
extends Exception {
    public FuseUnavailableMountException(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AppFuse mount point ");
        stringBuilder.append(n);
        stringBuilder.append(" is unavailable");
        super(stringBuilder.toString());
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;

public class EncodeException
extends Exception {
    public static final int ERROR_EXCEED_SIZE = 1;
    public static final int ERROR_UNENCODABLE = 0;
    private int mError;

    public EncodeException() {
        this.mError = 0;
    }

    @UnsupportedAppUsage
    public EncodeException(char c) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unencodable char: '");
        stringBuilder.append(c);
        stringBuilder.append("'");
        super(stringBuilder.toString());
        this.mError = 0;
    }

    @UnsupportedAppUsage
    public EncodeException(String string2) {
        super(string2);
        this.mError = 0;
    }

    public EncodeException(String string2, int n) {
        super(string2);
        this.mError = 0;
        this.mError = n;
    }

    public int getError() {
        return this.mError;
    }
}


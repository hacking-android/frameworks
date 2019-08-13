/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import java.io.IOException;

public final class RequestException
extends Exception {
    public RequestException(IOException iOException) {
        super(iOException);
    }

    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
}


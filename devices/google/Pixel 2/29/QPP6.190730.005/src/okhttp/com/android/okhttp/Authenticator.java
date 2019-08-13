/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Request;
import com.android.okhttp.Response;
import java.io.IOException;
import java.net.Proxy;

public interface Authenticator {
    public Request authenticate(Proxy var1, Response var2) throws IOException;

    public Request authenticateProxy(Proxy var1, Response var2) throws IOException;
}


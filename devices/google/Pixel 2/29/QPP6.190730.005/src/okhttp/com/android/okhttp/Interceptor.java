/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Connection;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import java.io.IOException;

public interface Interceptor {
    public Response intercept(Chain var1) throws IOException;

    public static interface Chain {
        public Connection connection();

        public Response proceed(Request var1) throws IOException;

        public Request request();
    }

}


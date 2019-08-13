/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.HttpUrl;
import com.android.okhttp.Request;
import java.net.Proxy;

public final class RequestLine {
    private RequestLine() {
    }

    static String get(Request request, Proxy.Type type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.method());
        stringBuilder.append(' ');
        if (RequestLine.includeAuthorityInRequestLine(request, type)) {
            stringBuilder.append(request.httpUrl());
        } else {
            stringBuilder.append(RequestLine.requestPath(request.httpUrl()));
        }
        stringBuilder.append(" HTTP/1.1");
        return stringBuilder.toString();
    }

    private static boolean includeAuthorityInRequestLine(Request request, Proxy.Type type) {
        boolean bl = !request.isHttps() && type == Proxy.Type.HTTP;
        return bl;
    }

    public static String requestPath(HttpUrl object) {
        String string = ((HttpUrl)object).encodedPath();
        if ((object = ((HttpUrl)object).encodedQuery()) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append('?');
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
        } else {
            object = string;
        }
        return object;
    }
}


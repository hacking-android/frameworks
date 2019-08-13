/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

public final class HttpMethod {
    private HttpMethod() {
    }

    public static boolean invalidatesCache(String string) {
        boolean bl = string.equals("POST") || string.equals("PATCH") || string.equals("PUT") || string.equals("DELETE") || string.equals("MOVE");
        return bl;
    }

    public static boolean permitsRequestBody(String string) {
        boolean bl = HttpMethod.requiresRequestBody(string) || string.equals("OPTIONS") || string.equals("DELETE") || string.equals("PROPFIND") || string.equals("MKCOL") || string.equals("LOCK");
        return bl;
    }

    public static boolean redirectsToGet(String string) {
        return string.equals("PROPFIND") ^ true;
    }

    public static boolean requiresRequestBody(String string) {
        boolean bl = string.equals("POST") || string.equals("PUT") || string.equals("PATCH") || string.equals("PROPPATCH") || string.equals("REPORT");
        return bl;
    }
}


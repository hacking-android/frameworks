/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import java.io.IOException;

public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2");
    
    private final String protocol;

    private Protocol(String string2) {
        this.protocol = string2;
    }

    public static Protocol get(String string) throws IOException {
        if (string.equals(Protocol.HTTP_1_0.protocol)) {
            return HTTP_1_0;
        }
        if (string.equals(Protocol.HTTP_1_1.protocol)) {
            return HTTP_1_1;
        }
        if (string.equals(Protocol.HTTP_2.protocol)) {
            return HTTP_2;
        }
        if (string.equals(Protocol.SPDY_3.protocol)) {
            return SPDY_3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected protocol: ");
        stringBuilder.append(string);
        throw new IOException(stringBuilder.toString());
    }

    public String toString() {
        return this.protocol;
    }
}


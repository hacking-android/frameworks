/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;

public class HttpRetryException
extends IOException {
    private static final long serialVersionUID = -9186022286469111381L;
    private String location;
    private int responseCode;

    public HttpRetryException(String string, int n) {
        super(string);
        this.responseCode = n;
    }

    public HttpRetryException(String string, int n, String string2) {
        super(string);
        this.responseCode = n;
        this.location = string2;
    }

    public String getLocation() {
        return this.location;
    }

    public String getReason() {
        return super.getMessage();
    }

    public int responseCode() {
        return this.responseCode;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.net;

public class ParseException
extends RuntimeException {
    public String response;

    ParseException(String string2) {
        super(string2);
        this.response = string2;
    }

    ParseException(String string2, Throwable throwable) {
        super(string2, throwable);
        this.response = string2;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.okio.ByteString;
import java.io.UnsupportedEncodingException;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String charSequence, String string) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(":");
            stringBuilder.append(string);
            string = ByteString.of(stringBuilder.toString().getBytes("ISO-8859-1")).base64();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Basic ");
            ((StringBuilder)charSequence).append(string);
            charSequence = ((StringBuilder)charSequence).toString();
            return charSequence;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError();
        }
    }
}


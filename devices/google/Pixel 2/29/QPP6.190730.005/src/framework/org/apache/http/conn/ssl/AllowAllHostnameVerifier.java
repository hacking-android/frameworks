/*
 * Decompiled with CFR 0.145.
 */
package org.apache.http.conn.ssl;

import org.apache.http.conn.ssl.AbstractVerifier;

@Deprecated
public class AllowAllHostnameVerifier
extends AbstractVerifier {
    public final String toString() {
        return "ALLOW_ALL";
    }

    @Override
    public final void verify(String string2, String[] arrstring, String[] arrstring2) {
    }
}


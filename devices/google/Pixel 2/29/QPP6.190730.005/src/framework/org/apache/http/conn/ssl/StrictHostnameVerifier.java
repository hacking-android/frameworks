/*
 * Decompiled with CFR 0.145.
 */
package org.apache.http.conn.ssl;

import javax.net.ssl.SSLException;
import org.apache.http.conn.ssl.AbstractVerifier;

@Deprecated
public class StrictHostnameVerifier
extends AbstractVerifier {
    public final String toString() {
        return "STRICT";
    }

    @Override
    public final void verify(String string2, String[] arrstring, String[] arrstring2) throws SSLException {
        this.verify(string2, arrstring, arrstring2, true);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import javax.net.ssl.SSLSession;

public interface ConscryptHostnameVerifier {
    public boolean verify(String var1, SSLSession var2);
}


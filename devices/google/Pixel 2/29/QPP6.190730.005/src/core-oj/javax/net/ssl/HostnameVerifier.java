/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import javax.net.ssl.SSLSession;

public interface HostnameVerifier {
    public boolean verify(String var1, SSLSession var2);
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

public abstract class ApplicationProtocolSelector {
    public abstract String selectApplicationProtocol(SSLEngine var1, List<String> var2);

    public abstract String selectApplicationProtocol(SSLSocket var1, List<String> var2);
}


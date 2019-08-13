/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import javax.net.ssl.SSLSession;

interface SSLServerSessionCache {
    public byte[] getSessionData(byte[] var1);

    public void putSessionData(SSLSession var1, byte[] var2);
}


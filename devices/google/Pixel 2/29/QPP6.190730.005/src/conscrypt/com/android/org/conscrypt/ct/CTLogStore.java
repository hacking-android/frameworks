/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.CTLogInfo;

public interface CTLogStore {
    public CTLogInfo getKnownLog(byte[] var1);
}


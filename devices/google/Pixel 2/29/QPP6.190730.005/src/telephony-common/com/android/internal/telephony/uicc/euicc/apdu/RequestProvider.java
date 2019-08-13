/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc.apdu;

import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public interface RequestProvider {
    public void buildRequest(byte[] var1, RequestBuilder var2) throws Throwable;
}


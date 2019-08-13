/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.net;

import android.net.INetdEventCallback;

public class BaseNetdEventCallback
extends INetdEventCallback.Stub {
    @Override
    public void onConnectEvent(String string2, int n, long l, int n2) {
    }

    @Override
    public void onDnsEvent(int n, int n2, int n3, String string2, String[] arrstring, int n4, long l, int n5) {
    }

    @Override
    public void onNat64PrefixEvent(int n, boolean bl, String string2, int n2) {
    }

    @Override
    public void onPrivateDnsValidationEvent(int n, String string2, String string3, boolean bl) {
    }
}


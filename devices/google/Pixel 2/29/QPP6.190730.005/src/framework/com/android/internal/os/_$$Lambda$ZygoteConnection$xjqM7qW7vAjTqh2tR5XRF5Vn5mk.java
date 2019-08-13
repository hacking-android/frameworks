/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.os.ZygoteConnection;

public final class _$$Lambda$ZygoteConnection$xjqM7qW7vAjTqh2tR5XRF5Vn5mk
implements Runnable {
    private final /* synthetic */ String[] f$0;

    public /* synthetic */ _$$Lambda$ZygoteConnection$xjqM7qW7vAjTqh2tR5XRF5Vn5mk(String[] arrstring) {
        this.f$0 = arrstring;
    }

    @Override
    public final void run() {
        ZygoteConnection.lambda$handleApiBlacklistExemptions$0(this.f$0);
    }
}


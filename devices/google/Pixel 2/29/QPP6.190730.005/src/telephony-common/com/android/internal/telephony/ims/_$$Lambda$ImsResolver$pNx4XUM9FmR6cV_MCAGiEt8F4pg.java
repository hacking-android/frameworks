/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package com.android.internal.telephony.ims;

import android.os.Handler;
import android.os.Message;
import com.android.internal.telephony.ims.ImsResolver;

public final class _$$Lambda$ImsResolver$pNx4XUM9FmR6cV_MCAGiEt8F4pg
implements Handler.Callback {
    private final /* synthetic */ ImsResolver f$0;

    public /* synthetic */ _$$Lambda$ImsResolver$pNx4XUM9FmR6cV_MCAGiEt8F4pg(ImsResolver imsResolver) {
        this.f$0 = imsResolver;
    }

    public final boolean handleMessage(Message message) {
        return this.f$0.lambda$new$0$ImsResolver(message);
    }
}


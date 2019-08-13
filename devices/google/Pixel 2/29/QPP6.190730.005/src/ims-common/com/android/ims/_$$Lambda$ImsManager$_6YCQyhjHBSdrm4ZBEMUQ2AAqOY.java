/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import com.android.ims.ImsManager;

public final class _$$Lambda$ImsManager$_6YCQyhjHBSdrm4ZBEMUQ2AAqOY
implements Runnable {
    private final /* synthetic */ ImsManager f$0;
    private final /* synthetic */ boolean f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ImsManager$_6YCQyhjHBSdrm4ZBEMUQ2AAqOY(ImsManager imsManager, boolean bl, int n) {
        this.f$0 = imsManager;
        this.f$1 = bl;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setRttConfig$4$ImsManager(this.f$1, this.f$2);
    }
}


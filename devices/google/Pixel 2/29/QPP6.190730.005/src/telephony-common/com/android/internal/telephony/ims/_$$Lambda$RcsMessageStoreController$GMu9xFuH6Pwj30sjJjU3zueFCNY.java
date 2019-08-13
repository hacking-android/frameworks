/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$GMu9xFuH6Pwj30sjJjU3zueFCNY
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$GMu9xFuH6Pwj30sjJjU3zueFCNY(RcsMessageStoreController rcsMessageStoreController, int n, String string) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = string;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setRcsParticipantAlias$15$RcsMessageStoreController(this.f$1, this.f$2);
    }
}


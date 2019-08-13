/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingLayout;

public final class _$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg
implements Runnable {
    private final /* synthetic */ MessagingLayout f$0;
    private final /* synthetic */ MessagingGroup f$1;

    public /* synthetic */ _$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg(MessagingLayout messagingLayout, MessagingGroup messagingGroup) {
        this.f$0 = messagingLayout;
        this.f$1 = messagingGroup;
    }

    @Override
    public final void run() {
        this.f$0.lambda$removeGroups$0$MessagingLayout(this.f$1);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import com.android.internal.widget.MessagingGroup;

public final class _$$Lambda$MessagingGroup$QKnXYzCylYJqF8wEQG98SXlcu2M
implements Runnable {
    private final /* synthetic */ MessagingGroup f$0;
    private final /* synthetic */ Runnable f$1;

    public /* synthetic */ _$$Lambda$MessagingGroup$QKnXYzCylYJqF8wEQG98SXlcu2M(MessagingGroup messagingGroup, Runnable runnable) {
        this.f$0 = messagingGroup;
        this.f$1 = runnable;
    }

    @Override
    public final void run() {
        this.f$0.lambda$removeGroupAnimated$1$MessagingGroup(this.f$1);
    }
}


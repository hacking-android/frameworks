/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import com.android.internal.util.FunctionalUtils;
import java.util.function.Consumer;

public final class _$$Lambda$FunctionalUtils$koCSI8D7Nu5vOJTVTEj0m3leo_U
implements Runnable {
    private final /* synthetic */ FunctionalUtils.ThrowingRunnable f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$FunctionalUtils$koCSI8D7Nu5vOJTVTEj0m3leo_U(FunctionalUtils.ThrowingRunnable throwingRunnable, Consumer consumer) {
        this.f$0 = throwingRunnable;
        this.f$1 = consumer;
    }

    @Override
    public final void run() {
        FunctionalUtils.lambda$handleExceptions$0(this.f$0, this.f$1);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.app.timezone.RulesManager;

public final class _$$Lambda$RulesManager$CallbackWrapper$t7a48uTTxaRuSo3YBKxBIbPQznY
implements Runnable {
    private final /* synthetic */ RulesManager.CallbackWrapper f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$RulesManager$CallbackWrapper$t7a48uTTxaRuSo3YBKxBIbPQznY(RulesManager.CallbackWrapper callbackWrapper, int n) {
        this.f$0 = callbackWrapper;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onFinished$0$RulesManager$CallbackWrapper(this.f$1);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.Call;

public final class _$$Lambda$Call$bt1B6cq3ylYqEtzOXnJWMeJ_ojc
implements Runnable {
    private final /* synthetic */ Call.Callback f$0;
    private final /* synthetic */ Call f$1;

    public /* synthetic */ _$$Lambda$Call$bt1B6cq3ylYqEtzOXnJWMeJ_ojc(Call.Callback callback, Call call) {
        this.f$0 = callback;
        this.f$1 = call;
    }

    @Override
    public final void run() {
        Call.lambda$internalOnHandoverComplete$3(this.f$0, this.f$1);
    }
}


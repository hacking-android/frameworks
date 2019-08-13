/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.Call;

public final class _$$Lambda$Call$JyYlHynNNc3DTrfrP5aXatJNft4
implements Runnable {
    private final /* synthetic */ Call.Callback f$0;
    private final /* synthetic */ Call f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$Call$JyYlHynNNc3DTrfrP5aXatJNft4(Call.Callback callback, Call call, int n) {
        this.f$0 = callback;
        this.f$1 = call;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        Call.lambda$internalOnRttInitiationFailure$1(this.f$0, this.f$1, this.f$2);
    }
}


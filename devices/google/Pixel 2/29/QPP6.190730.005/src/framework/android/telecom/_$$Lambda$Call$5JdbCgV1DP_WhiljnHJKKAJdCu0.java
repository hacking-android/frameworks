/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.Call;

public final class _$$Lambda$Call$5JdbCgV1DP_WhiljnHJKKAJdCu0
implements Runnable {
    private final /* synthetic */ Call.Callback f$0;
    private final /* synthetic */ Call f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ Call.RttCall f$3;

    public /* synthetic */ _$$Lambda$Call$5JdbCgV1DP_WhiljnHJKKAJdCu0(Call.Callback callback, Call call, boolean bl, Call.RttCall rttCall) {
        this.f$0 = callback;
        this.f$1 = call;
        this.f$2 = bl;
        this.f$3 = rttCall;
    }

    @Override
    public final void run() {
        Call.lambda$fireOnIsRttChanged$4(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}


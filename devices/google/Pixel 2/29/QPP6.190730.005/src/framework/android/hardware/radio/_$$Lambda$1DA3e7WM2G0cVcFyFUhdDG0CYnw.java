/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;

public final class _$$Lambda$1DA3e7WM2G0cVcFyFUhdDG0CYnw
implements Runnable {
    private final /* synthetic */ ProgramList.OnCompleteListener f$0;

    public /* synthetic */ _$$Lambda$1DA3e7WM2G0cVcFyFUhdDG0CYnw(ProgramList.OnCompleteListener onCompleteListener) {
        this.f$0 = onCompleteListener;
    }

    @Override
    public final void run() {
        this.f$0.onComplete();
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.RadioManager;
import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$RSNrzX5_O3nayC2_jg0kAR6KkKY
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ RadioManager.ProgramInfo f$1;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$RSNrzX5_O3nayC2_jg0kAR6KkKY(TunerCallbackAdapter tunerCallbackAdapter, RadioManager.ProgramInfo programInfo) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = programInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCurrentProgramInfoChanged$6$TunerCallbackAdapter(this.f$1);
    }
}


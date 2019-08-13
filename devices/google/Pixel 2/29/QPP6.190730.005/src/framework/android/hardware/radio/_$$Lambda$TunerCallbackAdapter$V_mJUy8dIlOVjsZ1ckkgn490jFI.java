/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$V_mJUy8dIlOVjsZ1ckkgn490jFI
implements ProgramList.OnCompleteListener {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ ProgramList f$1;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$V_mJUy8dIlOVjsZ1ckkgn490jFI(TunerCallbackAdapter tunerCallbackAdapter, ProgramList programList) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = programList;
    }

    @Override
    public final void onComplete() {
        this.f$0.lambda$setProgramListObserver$1$TunerCallbackAdapter(this.f$1);
    }
}


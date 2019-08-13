/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y
implements ProgramList.OnCloseListener {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ ProgramList f$1;
    private final /* synthetic */ ProgramList.OnCloseListener f$2;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y(TunerCallbackAdapter tunerCallbackAdapter, ProgramList programList, ProgramList.OnCloseListener onCloseListener) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = programList;
        this.f$2 = onCloseListener;
    }

    @Override
    public final void onClose() {
        this.f$0.lambda$setProgramListObserver$0$TunerCallbackAdapter(this.f$1, this.f$2);
    }
}


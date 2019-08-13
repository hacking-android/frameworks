/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.TunerAdapter;

public final class _$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c
implements ProgramList.OnCloseListener {
    private final /* synthetic */ TunerAdapter f$0;

    public /* synthetic */ _$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c(TunerAdapter tunerAdapter) {
        this.f$0 = tunerAdapter;
    }

    @Override
    public final void onClose() {
        this.f$0.lambda$getDynamicProgramList$1$TunerAdapter();
    }
}


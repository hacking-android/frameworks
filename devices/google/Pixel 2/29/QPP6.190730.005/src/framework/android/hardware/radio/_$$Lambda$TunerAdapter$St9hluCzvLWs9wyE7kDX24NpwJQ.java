/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$TunerAdapter
 *  android.hardware.radio.-$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ
 */
package android.hardware.radio;

import android.hardware.radio.-$;
import android.hardware.radio.ProgramList;
import android.hardware.radio.TunerAdapter;

public final class _$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ
implements ProgramList.OnCloseListener {
    public static final /* synthetic */ -$.Lambda.TunerAdapter.St9hluCzvLWs9wyE7kDX24NpwJQ INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ();
    }

    private /* synthetic */ _$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ() {
    }

    @Override
    public final void onClose() {
        TunerAdapter.lambda$getDynamicProgramList$2();
    }
}


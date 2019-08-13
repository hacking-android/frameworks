/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$TunerAdapter
 *  android.hardware.radio.-$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA
 */
package android.hardware.radio;

import android.hardware.radio.-$;
import android.hardware.radio.ProgramList;
import android.hardware.radio.TunerAdapter;

public final class _$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA
implements ProgramList.OnCloseListener {
    public static final /* synthetic */ -$.Lambda.TunerAdapter.xm27iP_3PUgByOaDoK2KJcP5fnA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA();
    }

    private /* synthetic */ _$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA() {
    }

    @Override
    public final void onClose() {
        TunerAdapter.lambda$getProgramList$0();
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.accessibility.-$
 *  com.android.internal.accessibility.-$$Lambda
 *  com.android.internal.accessibility.-$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv-R8
 */
package com.android.internal.accessibility;

import android.speech.tts.TextToSpeech;
import com.android.internal.accessibility.-$;
import java.util.function.Consumer;

public final class _$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv_R8
implements Consumer {
    public static final /* synthetic */ -$.Lambda.qdzoyIBhDB17ZFWPp1Rf8ICv-R8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv_R8();
    }

    private /* synthetic */ _$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv_R8() {
    }

    public final void accept(Object object) {
        ((TextToSpeech)object).shutdown();
    }
}


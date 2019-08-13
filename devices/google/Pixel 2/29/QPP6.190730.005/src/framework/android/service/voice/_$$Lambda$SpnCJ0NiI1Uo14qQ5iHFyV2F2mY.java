/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.voice.-$
 *  android.service.voice.-$$Lambda
 *  android.service.voice.-$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY
 */
package android.service.voice;

import android.service.voice.-$;
import android.service.voice.VoiceInteractionService;
import java.util.function.Consumer;

public final class _$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY
implements Consumer {
    public static final /* synthetic */ -$.Lambda.SpnCJ0NiI1Uo14qQ5iHFyV2F2mY INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY();
    }

    private /* synthetic */ _$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY() {
    }

    public final void accept(Object object) {
        ((VoiceInteractionService)object).onReady();
    }
}


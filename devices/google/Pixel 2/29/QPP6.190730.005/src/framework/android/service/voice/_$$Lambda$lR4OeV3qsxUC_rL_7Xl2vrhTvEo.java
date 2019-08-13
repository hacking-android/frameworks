/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.voice.-$
 *  android.service.voice.-$$Lambda
 *  android.service.voice.-$$Lambda$lR4OeV3qsxUC-rL-7Xl2vrhTvEo
 */
package android.service.voice;

import android.service.voice.-$;
import android.service.voice.VoiceInteractionSession;
import java.util.function.BiConsumer;

public final class _$$Lambda$lR4OeV3qsxUC_rL_7Xl2vrhTvEo
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.lR4OeV3qsxUC-rL-7Xl2vrhTvEo INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$lR4OeV3qsxUC_rL_7Xl2vrhTvEo();
    }

    private /* synthetic */ _$$Lambda$lR4OeV3qsxUC_rL_7Xl2vrhTvEo() {
    }

    public final void accept(Object object, Object object2) {
        ((VoiceInteractionSession)object).onDirectActionsInvalidated((VoiceInteractionSession.ActivityId)object2);
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.voice.-$
 *  android.service.voice.-$$Lambda
 *  android.service.voice.-$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM
 */
package android.service.voice;

import android.service.voice.-$;
import android.service.voice.VoiceInteractionService;
import java.util.function.Consumer;

public final class _$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM
implements Consumer {
    public static final /* synthetic */ -$.Lambda.2vcT7tC5Khx2oNbQI6Zvwrft_YM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM();
    }

    private /* synthetic */ _$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM() {
    }

    public final void accept(Object object) {
        ((VoiceInteractionService)object).onLaunchVoiceAssistFromKeyguard();
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.soundtrigger.-$
 *  android.media.soundtrigger.-$$Lambda
 *  android.media.soundtrigger.-$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY
 */
package android.media.soundtrigger;

import android.media.soundtrigger.-$;
import android.media.soundtrigger.SoundTriggerDetectionService;
import android.os.Bundle;
import com.android.internal.util.function.QuintConsumer;
import java.util.UUID;

public final class _$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY
implements QuintConsumer {
    public static final /* synthetic */ -$.Lambda.oNgT3sYhSGVWlnU92bECo_ULGeY INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY();
    }

    private /* synthetic */ _$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4, Object object5) {
        ((SoundTriggerDetectionService)object).onError((UUID)object2, (Bundle)object3, (Integer)object4, (Integer)object5);
    }
}


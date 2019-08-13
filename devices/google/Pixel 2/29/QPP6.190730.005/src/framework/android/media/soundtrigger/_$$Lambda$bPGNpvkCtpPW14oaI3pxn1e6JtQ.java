/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.soundtrigger.-$
 *  android.media.soundtrigger.-$$Lambda
 *  android.media.soundtrigger.-$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ
 */
package android.media.soundtrigger;

import android.media.soundtrigger.-$;
import android.media.soundtrigger.SoundTriggerDetectionService;
import android.os.Bundle;
import com.android.internal.util.function.QuadConsumer;
import java.util.UUID;

public final class _$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ
implements QuadConsumer {
    public static final /* synthetic */ -$.Lambda.bPGNpvkCtpPW14oaI3pxn1e6JtQ INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ();
    }

    private /* synthetic */ _$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4) {
        ((SoundTriggerDetectionService)object).onStopOperation((UUID)object2, (Bundle)object3, (Integer)object4);
    }
}


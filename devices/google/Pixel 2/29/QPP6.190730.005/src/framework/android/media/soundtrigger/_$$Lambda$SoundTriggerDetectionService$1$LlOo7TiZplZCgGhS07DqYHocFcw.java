/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.soundtrigger.-$
 *  android.media.soundtrigger.-$$Lambda
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService$1
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw
 */
package android.media.soundtrigger;

import android.media.soundtrigger.-$;
import android.media.soundtrigger.ISoundTriggerDetectionServiceClient;
import android.media.soundtrigger.SoundTriggerDetectionService;
import android.os.Bundle;
import com.android.internal.util.function.QuadConsumer;
import java.util.UUID;

public final class _$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw
implements QuadConsumer {
    public static final /* synthetic */ -$.Lambda.SoundTriggerDetectionService.1.LlOo7TiZplZCgGhS07DqYHocFcw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw();
    }

    private /* synthetic */ _$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4) {
        SoundTriggerDetectionService.1.lambda$setClient$0((SoundTriggerDetectionService)object, (UUID)object2, (Bundle)object3, (ISoundTriggerDetectionServiceClient)object4);
    }
}


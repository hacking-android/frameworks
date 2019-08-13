/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.soundtrigger.-$
 *  android.media.soundtrigger.-$$Lambda
 *  android.media.soundtrigger.-$$Lambda$ISQYIYPBRBIOLBUJy7rrJW-SiJg
 */
package android.media.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.-$;
import android.media.soundtrigger.SoundTriggerDetectionService;
import android.os.Bundle;
import com.android.internal.util.function.QuintConsumer;
import java.util.UUID;

public final class _$$Lambda$ISQYIYPBRBIOLBUJy7rrJW_SiJg
implements QuintConsumer {
    public static final /* synthetic */ -$.Lambda.ISQYIYPBRBIOLBUJy7rrJW-SiJg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ISQYIYPBRBIOLBUJy7rrJW_SiJg();
    }

    private /* synthetic */ _$$Lambda$ISQYIYPBRBIOLBUJy7rrJW_SiJg() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4, Object object5) {
        ((SoundTriggerDetectionService)object).onGenericRecognitionEvent((UUID)object2, (Bundle)object3, (Integer)object4, (SoundTrigger.GenericRecognitionEvent)object5);
    }
}


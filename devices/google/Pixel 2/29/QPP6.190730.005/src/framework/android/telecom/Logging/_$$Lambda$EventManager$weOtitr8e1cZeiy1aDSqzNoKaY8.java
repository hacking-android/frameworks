/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.Logging.-$
 *  android.telecom.Logging.-$$Lambda
 *  android.telecom.Logging.-$$Lambda$EventManager
 *  android.telecom.Logging.-$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8
 */
package android.telecom.Logging;

import android.telecom.Logging.-$;
import android.telecom.Logging.EventManager;
import android.util.Pair;
import java.util.function.ToLongFunction;

public final class _$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8
implements ToLongFunction {
    public static final /* synthetic */ -$.Lambda.EventManager.weOtitr8e1cZeiy1aDSqzNoKaY8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8();
    }

    private /* synthetic */ _$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8() {
    }

    public final long applyAsLong(Object object) {
        return EventManager.lambda$dumpEventsTimeline$0((Pair)object);
    }
}


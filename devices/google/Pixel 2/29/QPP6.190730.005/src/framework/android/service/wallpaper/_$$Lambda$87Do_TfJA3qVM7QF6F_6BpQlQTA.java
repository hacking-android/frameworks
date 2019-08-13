/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.wallpaper.-$
 *  android.service.wallpaper.-$$Lambda
 *  android.service.wallpaper.-$$Lambda$87Do-TfJA3qVM7QF6F_6BpQlQTA
 */
package android.service.wallpaper;

import android.os.SystemClock;
import android.service.wallpaper.-$;
import java.util.function.Supplier;

public final class _$$Lambda$87Do_TfJA3qVM7QF6F_6BpQlQTA
implements Supplier {
    public static final /* synthetic */ -$.Lambda.87Do-TfJA3qVM7QF6F_6BpQlQTA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$87Do_TfJA3qVM7QF6F_6BpQlQTA();
    }

    private /* synthetic */ _$$Lambda$87Do_TfJA3qVM7QF6F_6BpQlQTA() {
    }

    public final Object get() {
        return SystemClock.elapsedRealtime();
    }
}


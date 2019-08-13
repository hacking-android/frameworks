/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.Logging.-$
 *  android.telecom.Logging.-$$Lambda
 *  android.telecom.Logging.-$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E
 */
package android.telecom.Logging;

import android.os.Process;
import android.telecom.Logging.-$;
import android.telecom.Logging.SessionManager;

public final class _$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E
implements SessionManager.ICurrentThreadId {
    public static final /* synthetic */ -$.Lambda.L5F_SL2jOCUETYvgdB36aGwY50E INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E();
    }

    private /* synthetic */ _$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E() {
    }

    @Override
    public final int get() {
        return Process.myTid();
    }
}


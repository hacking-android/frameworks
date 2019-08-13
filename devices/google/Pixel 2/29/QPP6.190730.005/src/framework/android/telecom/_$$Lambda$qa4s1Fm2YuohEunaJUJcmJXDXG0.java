/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.-$
 *  android.telecom.-$$Lambda
 *  android.telecom.-$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0
 */
package android.telecom;

import android.telecom.-$;
import android.telecom.Log;
import android.telecom.Logging.SessionManager;

public final class _$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0
implements SessionManager.ISessionIdQueryHandler {
    public static final /* synthetic */ -$.Lambda.qa4s1Fm2YuohEunaJUJcmJXDXG0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0();
    }

    private /* synthetic */ _$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0() {
    }

    @Override
    public final String getSessionId() {
        return Log.getSessionId();
    }
}


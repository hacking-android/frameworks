/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStoreException;

public abstract class RcsEvent {
    private final long mTimestamp;

    protected RcsEvent(long l) {
        this.mTimestamp = l;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    abstract void persist(RcsControllerCall var1) throws RcsMessageStoreException;
}


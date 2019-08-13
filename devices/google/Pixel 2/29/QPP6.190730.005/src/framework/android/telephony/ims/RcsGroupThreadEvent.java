/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsParticipant;

public abstract class RcsGroupThreadEvent
extends RcsEvent {
    private final RcsParticipant mOriginatingParticipant;
    private final RcsGroupThread mRcsGroupThread;

    RcsGroupThreadEvent(long l, RcsGroupThread rcsGroupThread, RcsParticipant rcsParticipant) {
        super(l);
        this.mRcsGroupThread = rcsGroupThread;
        this.mOriginatingParticipant = rcsParticipant;
    }

    public RcsParticipant getOriginatingParticipant() {
        return this.mOriginatingParticipant;
    }

    public RcsGroupThread getRcsGroupThread() {
        return this.mRcsGroupThread;
    }
}


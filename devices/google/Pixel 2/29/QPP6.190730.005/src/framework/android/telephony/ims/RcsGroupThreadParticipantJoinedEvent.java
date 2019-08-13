/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsGroupThreadEvent;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims._$$Lambda$RcsGroupThreadParticipantJoinedEvent$KF8KQ4WJfLnGm4G9rOgwA9MjEj8;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadParticipantJoinedEvent
extends RcsGroupThreadEvent {
    private final RcsParticipant mJoinedParticipantId;

    public RcsGroupThreadParticipantJoinedEvent(long l, RcsGroupThread rcsGroupThread, RcsParticipant rcsParticipant, RcsParticipant rcsParticipant2) {
        super(l, rcsGroupThread, rcsParticipant);
        this.mJoinedParticipantId = rcsParticipant2;
    }

    public RcsParticipant getJoinedParticipant() {
        return this.mJoinedParticipantId;
    }

    public /* synthetic */ Integer lambda$persist$0$RcsGroupThreadParticipantJoinedEvent(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.createGroupThreadParticipantJoinedEvent(this.getTimestamp(), this.getRcsGroupThread().getThreadId(), this.getOriginatingParticipant().getId(), this.getJoinedParticipant().getId(), string2);
    }

    @Override
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new _$$Lambda$RcsGroupThreadParticipantJoinedEvent$KF8KQ4WJfLnGm4G9rOgwA9MjEj8(this));
    }
}


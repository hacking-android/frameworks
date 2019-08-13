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
import android.telephony.ims._$$Lambda$RcsGroupThreadParticipantLeftEvent$vX6x1bZueUi684uTuoFiWxhgs80;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadParticipantLeftEvent
extends RcsGroupThreadEvent {
    private RcsParticipant mLeavingParticipant;

    public RcsGroupThreadParticipantLeftEvent(long l, RcsGroupThread rcsGroupThread, RcsParticipant rcsParticipant, RcsParticipant rcsParticipant2) {
        super(l, rcsGroupThread, rcsParticipant);
        this.mLeavingParticipant = rcsParticipant2;
    }

    public RcsParticipant getLeavingParticipant() {
        return this.mLeavingParticipant;
    }

    public /* synthetic */ Integer lambda$persist$0$RcsGroupThreadParticipantLeftEvent(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.createGroupThreadParticipantLeftEvent(this.getTimestamp(), this.getRcsGroupThread().getThreadId(), this.getOriginatingParticipant().getId(), this.getLeavingParticipant().getId(), string2);
    }

    @Override
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new _$$Lambda$RcsGroupThreadParticipantLeftEvent$vX6x1bZueUi684uTuoFiWxhgs80(this));
    }
}


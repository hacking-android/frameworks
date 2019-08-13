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
import android.telephony.ims._$$Lambda$RcsGroupThreadNameChangedEvent$_UcLy20x7aG6AEgcOgmZOeqTok0;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadNameChangedEvent
extends RcsGroupThreadEvent {
    private final String mNewName;

    public RcsGroupThreadNameChangedEvent(long l, RcsGroupThread rcsGroupThread, RcsParticipant rcsParticipant, String string2) {
        super(l, rcsGroupThread, rcsParticipant);
        this.mNewName = string2;
    }

    public String getNewName() {
        return this.mNewName;
    }

    public /* synthetic */ Integer lambda$persist$0$RcsGroupThreadNameChangedEvent(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.createGroupThreadNameChangedEvent(this.getTimestamp(), this.getRcsGroupThread().getThreadId(), this.getOriginatingParticipant().getId(), this.mNewName, string2);
    }

    @Override
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new _$$Lambda$RcsGroupThreadNameChangedEvent$_UcLy20x7aG6AEgcOgmZOeqTok0(this));
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims._$$Lambda$RcsParticipantAliasChangedEvent$iaidodGQwVEX4DZ8FekRuR_x3gQ;
import android.telephony.ims.aidl.IRcs;

public final class RcsParticipantAliasChangedEvent
extends RcsEvent {
    private final String mNewAlias;
    private final RcsParticipant mParticipant;

    public RcsParticipantAliasChangedEvent(long l, RcsParticipant rcsParticipant, String string2) {
        super(l);
        this.mParticipant = rcsParticipant;
        this.mNewAlias = string2;
    }

    public String getNewAlias() {
        return this.mNewAlias;
    }

    public RcsParticipant getParticipant() {
        return this.mParticipant;
    }

    public /* synthetic */ Integer lambda$persist$0$RcsParticipantAliasChangedEvent(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.createParticipantAliasChangedEvent(this.getTimestamp(), this.getParticipant().getId(), this.getNewAlias(), string2);
    }

    @Override
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new _$$Lambda$RcsParticipantAliasChangedEvent$iaidodGQwVEX4DZ8FekRuR_x3gQ(this));
    }
}


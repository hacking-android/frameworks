/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims._$$Lambda$RcsIncomingMessage$21fHX__vVRTL95x404C5b4eGWok;
import android.telephony.ims._$$Lambda$RcsIncomingMessage$FSzDY0_cZbSPckAubiU3QaXu_Yg;
import android.telephony.ims._$$Lambda$RcsIncomingMessage$OdAmvZkbLfGMknLzGuOOXKVYczw;
import android.telephony.ims._$$Lambda$RcsIncomingMessage$OvvfqgFG2FNYN7ohCBbWdETfeuQ;
import android.telephony.ims._$$Lambda$RcsIncomingMessage$ye8KwJqH7fqnRAZlQY1PRVyh2b0;
import android.telephony.ims.aidl.IRcs;

public class RcsIncomingMessage
extends RcsMessage {
    RcsIncomingMessage(RcsControllerCall rcsControllerCall, int n) {
        super(rcsControllerCall, n);
    }

    public long getArrivalTimestamp() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsIncomingMessage$FSzDY0_cZbSPckAubiU3QaXu_Yg(this));
    }

    public long getSeenTimestamp() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsIncomingMessage$21fHX__vVRTL95x404C5b4eGWok(this));
    }

    public RcsParticipant getSenderParticipant() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsIncomingMessage$ye8KwJqH7fqnRAZlQY1PRVyh2b0(this)));
    }

    @Override
    public boolean isIncoming() {
        return true;
    }

    public /* synthetic */ Long lambda$getArrivalTimestamp$1$RcsIncomingMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageArrivalTimestamp(this.mId, true, string2);
    }

    public /* synthetic */ Long lambda$getSeenTimestamp$3$RcsIncomingMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageSeenTimestamp(this.mId, true, string2);
    }

    public /* synthetic */ Integer lambda$getSenderParticipant$4$RcsIncomingMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getSenderParticipant(this.mId, string2);
    }

    public /* synthetic */ void lambda$setArrivalTimestamp$0$RcsIncomingMessage(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setMessageArrivalTimestamp(this.mId, true, l, string2);
    }

    public /* synthetic */ void lambda$setSeenTimestamp$2$RcsIncomingMessage(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setMessageSeenTimestamp(this.mId, true, l, string2);
    }

    public void setArrivalTimestamp(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsIncomingMessage$OdAmvZkbLfGMknLzGuOOXKVYczw(this, l));
    }

    public void setSeenTimestamp(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsIncomingMessage$OvvfqgFG2FNYN7ohCBbWdETfeuQ(this, l));
    }
}


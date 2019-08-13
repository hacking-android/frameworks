/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsOutgoingMessage;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims._$$Lambda$RcsOutgoingMessageDelivery$Hwf3ep_etCKWfwwAtq0Sdu0dtwY;
import android.telephony.ims._$$Lambda$RcsOutgoingMessageDelivery$P2OcWKWejNP6qsda0ef9G0jKYKs;
import android.telephony.ims._$$Lambda$RcsOutgoingMessageDelivery$RRb0ymf6fqzeTy7WOV3ylkaBJDA;
import android.telephony.ims._$$Lambda$RcsOutgoingMessageDelivery$XobnngqskscGHACfd0qrHXy_W6A;
import android.telephony.ims._$$Lambda$RcsOutgoingMessageDelivery$fxSVb_4v4N7q2YgopxM2Hg_pCH0;
import android.telephony.ims._$$Lambda$RcsOutgoingMessageDelivery$l9Yzsl9k4Z30dUsRJ0yJpKeg9jk;
import android.telephony.ims.aidl.IRcs;

public class RcsOutgoingMessageDelivery {
    private final RcsControllerCall mRcsControllerCall;
    private final int mRcsOutgoingMessageId;
    private final int mRecipientId;

    RcsOutgoingMessageDelivery(RcsControllerCall rcsControllerCall, int n, int n2) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRecipientId = n;
        this.mRcsOutgoingMessageId = n2;
    }

    public long getDeliveredTimestamp() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsOutgoingMessageDelivery$XobnngqskscGHACfd0qrHXy_W6A(this));
    }

    public RcsOutgoingMessage getMessage() {
        return new RcsOutgoingMessage(this.mRcsControllerCall, this.mRcsOutgoingMessageId);
    }

    public RcsParticipant getRecipient() {
        return new RcsParticipant(this.mRcsControllerCall, this.mRecipientId);
    }

    public long getSeenTimestamp() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsOutgoingMessageDelivery$fxSVb_4v4N7q2YgopxM2Hg_pCH0(this));
    }

    public int getStatus() throws RcsMessageStoreException {
        return (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsOutgoingMessageDelivery$Hwf3ep_etCKWfwwAtq0Sdu0dtwY(this));
    }

    public /* synthetic */ Long lambda$getDeliveredTimestamp$1$RcsOutgoingMessageDelivery(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getOutgoingDeliveryDeliveredTimestamp(this.mRcsOutgoingMessageId, this.mRecipientId, string2);
    }

    public /* synthetic */ Long lambda$getSeenTimestamp$3$RcsOutgoingMessageDelivery(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getOutgoingDeliverySeenTimestamp(this.mRcsOutgoingMessageId, this.mRecipientId, string2);
    }

    public /* synthetic */ Integer lambda$getStatus$5$RcsOutgoingMessageDelivery(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getOutgoingDeliveryStatus(this.mRcsOutgoingMessageId, this.mRecipientId, string2);
    }

    public /* synthetic */ void lambda$setDeliveredTimestamp$0$RcsOutgoingMessageDelivery(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setOutgoingDeliveryDeliveredTimestamp(this.mRcsOutgoingMessageId, this.mRecipientId, l, string2);
    }

    public /* synthetic */ void lambda$setSeenTimestamp$2$RcsOutgoingMessageDelivery(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setOutgoingDeliverySeenTimestamp(this.mRcsOutgoingMessageId, this.mRecipientId, l, string2);
    }

    public /* synthetic */ void lambda$setStatus$4$RcsOutgoingMessageDelivery(int n, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setOutgoingDeliveryStatus(this.mRcsOutgoingMessageId, this.mRecipientId, n, string2);
    }

    public void setDeliveredTimestamp(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsOutgoingMessageDelivery$RRb0ymf6fqzeTy7WOV3ylkaBJDA(this, l));
    }

    public void setSeenTimestamp(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsOutgoingMessageDelivery$P2OcWKWejNP6qsda0ef9G0jKYKs(this, l));
    }

    public void setStatus(int n) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsOutgoingMessageDelivery$l9Yzsl9k4Z30dUsRJ0yJpKeg9jk(this, n));
    }
}


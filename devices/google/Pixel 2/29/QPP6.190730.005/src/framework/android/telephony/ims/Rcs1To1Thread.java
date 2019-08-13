/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.RcsThread;
import android.telephony.ims._$$Lambda$Rcs1To1Thread$DlCgifrXUJFouqWWh_0GG6hzH_s;
import android.telephony.ims._$$Lambda$Rcs1To1Thread$_6gUCvjDS6WXqf0AClQwrZ7ZpSc;
import android.telephony.ims._$$Lambda$Rcs1To1Thread$vx_evSYitgJIMB6l_hANvSJpdBE;
import android.telephony.ims.aidl.IRcs;

public class Rcs1To1Thread
extends RcsThread {
    private int mThreadId;

    public Rcs1To1Thread(RcsControllerCall rcsControllerCall, int n) {
        super(rcsControllerCall, n);
        this.mThreadId = n;
    }

    public long getFallbackThreadId() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$Rcs1To1Thread$_6gUCvjDS6WXqf0AClQwrZ7ZpSc(this));
    }

    public RcsParticipant getRecipient() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, (Integer)this.mRcsControllerCall.call(new _$$Lambda$Rcs1To1Thread$DlCgifrXUJFouqWWh_0GG6hzH_s(this)));
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    public /* synthetic */ Long lambda$getFallbackThreadId$0$Rcs1To1Thread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.get1To1ThreadFallbackThreadId(this.mThreadId, string2);
    }

    public /* synthetic */ Integer lambda$getRecipient$2$Rcs1To1Thread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.get1To1ThreadOtherParticipantId(this.mThreadId, string2);
    }

    public /* synthetic */ void lambda$setFallbackThreadId$1$Rcs1To1Thread(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.set1To1ThreadFallbackThreadId(this.mThreadId, l, string2);
    }

    public void setFallbackThreadId(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$Rcs1To1Thread$vx_evSYitgJIMB6l_hANvSJpdBE(this, l));
    }
}


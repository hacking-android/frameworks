/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsIncomingMessage;
import android.telephony.ims.RcsIncomingMessageCreationParams;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageQueryResult;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsMessageSnippet;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsOutgoingMessage;
import android.telephony.ims.RcsOutgoingMessageCreationParams;
import android.telephony.ims._$$Lambda$RcsThread$9gFw0KtL_BczxOxCksL2zOV2xHM;
import android.telephony.ims._$$Lambda$RcsThread$A9iPL3bU3iiRv1xCYNUNP76n6Vw;
import android.telephony.ims._$$Lambda$RcsThread$TwqOqnkLjl05BhB2arTpJkBo73Y;
import android.telephony.ims._$$Lambda$RcsThread$_9zf_uqUJl6VjAbIMvQwKcAyzUs;
import android.telephony.ims._$$Lambda$RcsThread$uAkHFwrvypgP5w5y0Uy4uwQ6blY;
import android.telephony.ims.aidl.IRcs;
import com.android.internal.annotations.VisibleForTesting;

public abstract class RcsThread {
    protected final RcsControllerCall mRcsControllerCall;
    protected int mThreadId;

    protected RcsThread(RcsControllerCall rcsControllerCall, int n) {
        this.mThreadId = n;
        this.mRcsControllerCall = rcsControllerCall;
    }

    static /* synthetic */ RcsMessageQueryResultParcelable lambda$getMessages$4(RcsMessageQueryParams rcsMessageQueryParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessages(rcsMessageQueryParams, string2);
    }

    public RcsIncomingMessage addIncomingMessage(RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams) throws RcsMessageStoreException {
        int n = (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsThread$9gFw0KtL_BczxOxCksL2zOV2xHM(this, rcsIncomingMessageCreationParams));
        return new RcsIncomingMessage(this.mRcsControllerCall, n);
    }

    public RcsOutgoingMessage addOutgoingMessage(RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams) throws RcsMessageStoreException {
        int n = (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsThread$_9zf_uqUJl6VjAbIMvQwKcAyzUs(this, rcsOutgoingMessageCreationParams));
        return new RcsOutgoingMessage(this.mRcsControllerCall, n);
    }

    public void deleteMessage(RcsMessage rcsMessage) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsThread$uAkHFwrvypgP5w5y0Uy4uwQ6blY(this, rcsMessage));
    }

    public RcsMessageQueryResult getMessages() throws RcsMessageStoreException {
        RcsMessageQueryParams rcsMessageQueryParams = new RcsMessageQueryParams.Builder().setThread(this).build();
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsMessageQueryResult(rcsControllerCall, (RcsMessageQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsThread$A9iPL3bU3iiRv1xCYNUNP76n6Vw(rcsMessageQueryParams)));
    }

    public RcsMessageSnippet getSnippet() throws RcsMessageStoreException {
        return (RcsMessageSnippet)this.mRcsControllerCall.call(new _$$Lambda$RcsThread$TwqOqnkLjl05BhB2arTpJkBo73Y(this));
    }

    @VisibleForTesting
    public int getThreadId() {
        return this.mThreadId;
    }

    public int getThreadType() {
        return (int)this.isGroup();
    }

    public abstract boolean isGroup();

    public /* synthetic */ Integer lambda$addIncomingMessage$1$RcsThread(RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.addIncomingMessage(this.mThreadId, rcsIncomingMessageCreationParams, string2);
    }

    public /* synthetic */ Integer lambda$addOutgoingMessage$2$RcsThread(RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.addOutgoingMessage(this.mThreadId, rcsOutgoingMessageCreationParams, string2);
    }

    public /* synthetic */ void lambda$deleteMessage$3$RcsThread(RcsMessage rcsMessage, IRcs iRcs, String string2) throws RemoteException {
        iRcs.deleteMessage(rcsMessage.getId(), rcsMessage.isIncoming(), this.mThreadId, this.isGroup(), string2);
    }

    public /* synthetic */ RcsMessageSnippet lambda$getSnippet$0$RcsThread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageSnippet(this.mThreadId, string2);
    }
}


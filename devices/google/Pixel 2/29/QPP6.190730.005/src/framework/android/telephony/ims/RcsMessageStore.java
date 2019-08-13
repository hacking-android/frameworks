/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.Rcs1To1Thread;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsEventQueryParams;
import android.telephony.ims.RcsEventQueryResult;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageQueryResult;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.RcsParticipantQueryResult;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThread;
import android.telephony.ims.RcsThreadQueryParams;
import android.telephony.ims.RcsThreadQueryResult;
import android.telephony.ims.RcsThreadQueryResultParcelable;
import android.telephony.ims._$$Lambda$RcsMessageStore$5QXAY7bGFdmsWgLF0pk1tyYYovg;
import android.telephony.ims._$$Lambda$RcsMessageStore$720PbSnOJzhKXiqHw1UEfx5w_6A;
import android.telephony.ims._$$Lambda$RcsMessageStore$IvBKppwBc6MDwzIkAi2XJcVB_iI;
import android.telephony.ims._$$Lambda$RcsMessageStore$RFZerRPNR1WyCuEIu6_yEveDhrk;
import android.telephony.ims._$$Lambda$RcsMessageStore$XArwINUevYo_Ol_OgZskFwRkGhs;
import android.telephony.ims._$$Lambda$RcsMessageStore$d1Om4XlR70Dyh7qD9d6F4NZZkQI;
import android.telephony.ims._$$Lambda$RcsMessageStore$eOFObBGn_N5PMKJvVTBw06iJWQ4;
import android.telephony.ims._$$Lambda$RcsMessageStore$fs2V7Gtqd2gkYR7NanLG2NjZNho;
import android.telephony.ims._$$Lambda$RcsMessageStore$g309WUVpYx8N7s_uWdUAGJXtJOs;
import android.telephony.ims._$$Lambda$RcsMessageStore$nbXWLR_ux8VCEHNEyE7JO0J05YI;
import android.telephony.ims._$$Lambda$RcsMessageStore$tSyQsX68KutSWLEXxfgNSJ47ep0;
import android.telephony.ims._$$Lambda$RcsMessageStore$z090Zf4wxRrBwUxXanwm4N3vb7w;
import android.telephony.ims.aidl.IRcs;
import java.util.List;

public class RcsMessageStore {
    RcsControllerCall mRcsControllerCall;

    RcsMessageStore(Context context) {
        this.mRcsControllerCall = new RcsControllerCall(context);
    }

    static /* synthetic */ Integer lambda$createGroupThread$9(int[] arrn, String string2, Uri uri, IRcs iRcs, String string3) throws RemoteException {
        return iRcs.createGroupThread(arrn, string2, uri, string3);
    }

    static /* synthetic */ Integer lambda$createRcs1To1Thread$8(RcsParticipant rcsParticipant, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.createRcs1To1Thread(rcsParticipant.getId(), string2);
    }

    static /* synthetic */ Integer lambda$createRcsParticipant$11(String string2, String string3, IRcs iRcs, String string4) throws RemoteException {
        return iRcs.createRcsParticipant(string2, string3, string4);
    }

    static /* synthetic */ Boolean lambda$deleteThread$10(RcsThread rcsThread, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.deleteThread(rcsThread.getThreadId(), rcsThread.getThreadType(), string2);
    }

    static /* synthetic */ RcsEventQueryResultDescriptor lambda$getRcsEvents$6(RcsEventQueryParams rcsEventQueryParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getEvents(rcsEventQueryParams, string2);
    }

    static /* synthetic */ RcsEventQueryResultDescriptor lambda$getRcsEvents$7(RcsQueryContinuationToken rcsQueryContinuationToken, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getEventsWithToken(rcsQueryContinuationToken, string2);
    }

    static /* synthetic */ RcsMessageQueryResultParcelable lambda$getRcsMessages$4(RcsMessageQueryParams rcsMessageQueryParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessages(rcsMessageQueryParams, string2);
    }

    static /* synthetic */ RcsMessageQueryResultParcelable lambda$getRcsMessages$5(RcsQueryContinuationToken rcsQueryContinuationToken, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessagesWithToken(rcsQueryContinuationToken, string2);
    }

    static /* synthetic */ RcsParticipantQueryResultParcelable lambda$getRcsParticipants$2(RcsParticipantQueryParams rcsParticipantQueryParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getParticipants(rcsParticipantQueryParams, string2);
    }

    static /* synthetic */ RcsParticipantQueryResultParcelable lambda$getRcsParticipants$3(RcsQueryContinuationToken rcsQueryContinuationToken, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getParticipantsWithToken(rcsQueryContinuationToken, string2);
    }

    static /* synthetic */ RcsThreadQueryResultParcelable lambda$getRcsThreads$0(RcsThreadQueryParams rcsThreadQueryParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getRcsThreads(rcsThreadQueryParams, string2);
    }

    static /* synthetic */ RcsThreadQueryResultParcelable lambda$getRcsThreads$1(RcsQueryContinuationToken rcsQueryContinuationToken, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getRcsThreadsWithToken(rcsQueryContinuationToken, string2);
    }

    public RcsGroupThread createGroupThread(List<RcsParticipant> list, String string2, Uri uri) throws RcsMessageStoreException {
        int n;
        int[] arrn = null;
        if (list != null) {
            int[] arrn2 = new int[list.size()];
            n = 0;
            do {
                arrn = arrn2;
                if (n >= list.size()) break;
                arrn2[n] = list.get(n).getId();
                ++n;
            } while (true);
        }
        n = (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsMessageStore$g309WUVpYx8N7s_uWdUAGJXtJOs(arrn, string2, uri));
        return new RcsGroupThread(this.mRcsControllerCall, n);
    }

    public Rcs1To1Thread createRcs1To1Thread(RcsParticipant rcsParticipant) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new Rcs1To1Thread(rcsControllerCall, (Integer)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$eOFObBGn_N5PMKJvVTBw06iJWQ4(rcsParticipant)));
    }

    public RcsParticipant createRcsParticipant(String string2, String string3) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsParticipant(rcsControllerCall, (Integer)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$d1Om4XlR70Dyh7qD9d6F4NZZkQI(string2, string3)));
    }

    public void deleteThread(RcsThread rcsThread) throws RcsMessageStoreException {
        if (rcsThread == null) {
            return;
        }
        if (((Boolean)this.mRcsControllerCall.call(new _$$Lambda$RcsMessageStore$nbXWLR_ux8VCEHNEyE7JO0J05YI(rcsThread))).booleanValue()) {
            return;
        }
        throw new RcsMessageStoreException("Could not delete RcsThread");
    }

    public RcsEventQueryResult getRcsEvents(RcsEventQueryParams rcsEventQueryParams) throws RcsMessageStoreException {
        return ((RcsEventQueryResultDescriptor)this.mRcsControllerCall.call(new _$$Lambda$RcsMessageStore$IvBKppwBc6MDwzIkAi2XJcVB_iI(rcsEventQueryParams))).getRcsEventQueryResult(this.mRcsControllerCall);
    }

    public RcsEventQueryResult getRcsEvents(RcsQueryContinuationToken rcsQueryContinuationToken) throws RcsMessageStoreException {
        return ((RcsEventQueryResultDescriptor)this.mRcsControllerCall.call(new _$$Lambda$RcsMessageStore$RFZerRPNR1WyCuEIu6_yEveDhrk(rcsQueryContinuationToken))).getRcsEventQueryResult(this.mRcsControllerCall);
    }

    public RcsMessageQueryResult getRcsMessages(RcsMessageQueryParams rcsMessageQueryParams) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsMessageQueryResult(rcsControllerCall, (RcsMessageQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$5QXAY7bGFdmsWgLF0pk1tyYYovg(rcsMessageQueryParams)));
    }

    public RcsMessageQueryResult getRcsMessages(RcsQueryContinuationToken rcsQueryContinuationToken) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsMessageQueryResult(rcsControllerCall, (RcsMessageQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$fs2V7Gtqd2gkYR7NanLG2NjZNho(rcsQueryContinuationToken)));
    }

    public RcsParticipantQueryResult getRcsParticipants(RcsParticipantQueryParams rcsParticipantQueryParams) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsParticipantQueryResult(rcsControllerCall, (RcsParticipantQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$720PbSnOJzhKXiqHw1UEfx5w_6A(rcsParticipantQueryParams)));
    }

    public RcsParticipantQueryResult getRcsParticipants(RcsQueryContinuationToken rcsQueryContinuationToken) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsParticipantQueryResult(rcsControllerCall, (RcsParticipantQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$tSyQsX68KutSWLEXxfgNSJ47ep0(rcsQueryContinuationToken)));
    }

    public RcsThreadQueryResult getRcsThreads(RcsQueryContinuationToken rcsQueryContinuationToken) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsThreadQueryResult(rcsControllerCall, (RcsThreadQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$XArwINUevYo_Ol_OgZskFwRkGhs(rcsQueryContinuationToken)));
    }

    public RcsThreadQueryResult getRcsThreads(RcsThreadQueryParams rcsThreadQueryParams) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsThreadQueryResult(rcsControllerCall, (RcsThreadQueryResultParcelable)rcsControllerCall.call(new _$$Lambda$RcsMessageStore$z090Zf4wxRrBwUxXanwm4N3vb7w(rcsThreadQueryParams)));
    }

    public void persistRcsEvent(RcsEvent rcsEvent) throws RcsMessageStoreException {
        rcsEvent.persist(this.mRcsControllerCall);
    }
}


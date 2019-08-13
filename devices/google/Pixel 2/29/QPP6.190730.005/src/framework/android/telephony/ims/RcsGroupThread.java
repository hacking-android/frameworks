/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.RcsParticipantQueryResult;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsThread;
import android.telephony.ims._$$Lambda$RcsGroupThread$2_3X4NWEVE7qw298P70JdcMW6oM;
import android.telephony.ims._$$Lambda$RcsGroupThread$4K1iTAEPwdeTAbDd4wTsX1Jl4S4;
import android.telephony.ims._$$Lambda$RcsGroupThread$9QKuv_xqJEallZ_aE2sSumu3POo;
import android.telephony.ims._$$Lambda$RcsGroupThread$HaJSnZuef49b66N8v9ayzVaOQxQ;
import android.telephony.ims._$$Lambda$RcsGroupThread$LhWdWS6noezEn0xijClZdbKHOas;
import android.telephony.ims._$$Lambda$RcsGroupThread$OMEGtapvlm86Yn7pLPBR5He4UoQ;
import android.telephony.ims._$$Lambda$RcsGroupThread$X2eY_CkF7PfEGF8QwmaD6Cv0PhI;
import android.telephony.ims._$$Lambda$RcsGroupThread$ZorE2WcUPTtLCwMm_x5CnWwa7YI;
import android.telephony.ims._$$Lambda$RcsGroupThread$cwnjgWxIgjmTCKAe7pcICt4Voo0;
import android.telephony.ims._$$Lambda$RcsGroupThread$hYpkX2Z60Pf5FiSb6pvoBpmHfXA;
import android.telephony.ims._$$Lambda$RcsGroupThread$xvETBJ_gzJJ5zvelRSNsYZBdXKw;
import android.telephony.ims.aidl.IRcs;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RcsGroupThread
extends RcsThread {
    public RcsGroupThread(RcsControllerCall rcsControllerCall, int n) {
        super(rcsControllerCall, n);
    }

    static /* synthetic */ RcsParticipantQueryResultParcelable lambda$getParticipants$8(RcsParticipantQueryParams rcsParticipantQueryParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getParticipants(rcsParticipantQueryParams, string2);
    }

    public void addParticipant(RcsParticipant rcsParticipant) throws RcsMessageStoreException {
        if (rcsParticipant == null) {
            return;
        }
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsGroupThread$HaJSnZuef49b66N8v9ayzVaOQxQ(this, rcsParticipant));
    }

    public Uri getConferenceUri() throws RcsMessageStoreException {
        return (Uri)this.mRcsControllerCall.call(new _$$Lambda$RcsGroupThread$hYpkX2Z60Pf5FiSb6pvoBpmHfXA(this));
    }

    public Uri getGroupIcon() throws RcsMessageStoreException {
        return (Uri)this.mRcsControllerCall.call(new _$$Lambda$RcsGroupThread$4K1iTAEPwdeTAbDd4wTsX1Jl4S4(this));
    }

    public String getGroupName() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsGroupThread$cwnjgWxIgjmTCKAe7pcICt4Voo0(this));
    }

    public RcsParticipant getOwner() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsGroupThread$OMEGtapvlm86Yn7pLPBR5He4UoQ(this)));
    }

    public Set<RcsParticipant> getParticipants() throws RcsMessageStoreException {
        RcsParticipantQueryParams rcsParticipantQueryParams = new RcsParticipantQueryParams.Builder().setThread(this).build();
        return Collections.unmodifiableSet(new LinkedHashSet<RcsParticipant>(new RcsParticipantQueryResult(this.mRcsControllerCall, (RcsParticipantQueryResultParcelable)this.mRcsControllerCall.call(new _$$Lambda$RcsGroupThread$X2eY_CkF7PfEGF8QwmaD6Cv0PhI(rcsParticipantQueryParams))).getParticipants()));
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    public /* synthetic */ void lambda$addParticipant$6$RcsGroupThread(RcsParticipant rcsParticipant, IRcs iRcs, String string2) throws RemoteException {
        iRcs.addParticipantToGroupThread(this.mThreadId, rcsParticipant.getId(), string2);
    }

    public /* synthetic */ Uri lambda$getConferenceUri$9$RcsGroupThread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getGroupThreadConferenceUri(this.mThreadId, string2);
    }

    public /* synthetic */ Uri lambda$getGroupIcon$2$RcsGroupThread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getGroupThreadIcon(this.mThreadId, string2);
    }

    public /* synthetic */ String lambda$getGroupName$0$RcsGroupThread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getGroupThreadName(this.mThreadId, string2);
    }

    public /* synthetic */ Integer lambda$getOwner$4$RcsGroupThread(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getGroupThreadOwner(this.mThreadId, string2);
    }

    public /* synthetic */ void lambda$removeParticipant$7$RcsGroupThread(RcsParticipant rcsParticipant, IRcs iRcs, String string2) throws RemoteException {
        iRcs.removeParticipantFromGroupThread(this.mThreadId, rcsParticipant.getId(), string2);
    }

    public /* synthetic */ void lambda$setConferenceUri$10$RcsGroupThread(Uri uri, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setGroupThreadConferenceUri(this.mThreadId, uri, string2);
    }

    public /* synthetic */ void lambda$setGroupIcon$3$RcsGroupThread(Uri uri, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setGroupThreadIcon(this.mThreadId, uri, string2);
    }

    public /* synthetic */ void lambda$setGroupName$1$RcsGroupThread(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setGroupThreadName(this.mThreadId, string2, string3);
    }

    public /* synthetic */ void lambda$setOwner$5$RcsGroupThread(RcsParticipant rcsParticipant, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setGroupThreadOwner(this.mThreadId, rcsParticipant.getId(), string2);
    }

    public void removeParticipant(RcsParticipant rcsParticipant) throws RcsMessageStoreException {
        if (rcsParticipant == null) {
            return;
        }
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsGroupThread$xvETBJ_gzJJ5zvelRSNsYZBdXKw(this, rcsParticipant));
    }

    public void setConferenceUri(Uri uri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsGroupThread$LhWdWS6noezEn0xijClZdbKHOas(this, uri));
    }

    public void setGroupIcon(Uri uri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsGroupThread$2_3X4NWEVE7qw298P70JdcMW6oM(this, uri));
    }

    public void setGroupName(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsGroupThread$ZorE2WcUPTtLCwMm_x5CnWwa7YI(this, string2));
    }

    public void setOwner(RcsParticipant rcsParticipant) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsGroupThread$9QKuv_xqJEallZ_aE2sSumu3POo(this, rcsParticipant));
    }
}


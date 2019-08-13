/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsGroupThreadEvent;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims._$$Lambda$RcsGroupThreadIconChangedEvent$XfKd9jzuhr_hAT3mvSOBgWj08Js;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadIconChangedEvent
extends RcsGroupThreadEvent {
    private final Uri mNewIcon;

    public RcsGroupThreadIconChangedEvent(long l, RcsGroupThread rcsGroupThread, RcsParticipant rcsParticipant, Uri uri) {
        super(l, rcsGroupThread, rcsParticipant);
        this.mNewIcon = uri;
    }

    public Uri getNewIcon() {
        return this.mNewIcon;
    }

    public /* synthetic */ Integer lambda$persist$0$RcsGroupThreadIconChangedEvent(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.createGroupThreadIconChangedEvent(this.getTimestamp(), this.getRcsGroupThread().getThreadId(), this.getOriginatingParticipant().getId(), this.mNewIcon, string2);
    }

    @Override
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new _$$Lambda$RcsGroupThreadIconChangedEvent$XfKd9jzuhr_hAT3mvSOBgWj08Js(this));
    }
}


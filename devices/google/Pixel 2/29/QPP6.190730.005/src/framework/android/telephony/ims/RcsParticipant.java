/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims._$$Lambda$RcsParticipant$HgHlMU15W2RReyvhk_UQ_432pfA;
import android.telephony.ims._$$Lambda$RcsParticipant$MNtRFbM6h_ycH3bPEUZgB5f56zs;
import android.telephony.ims._$$Lambda$RcsParticipant$T35onLZnU_uRTl7zQ7ZWRFtFvx4;
import android.telephony.ims._$$Lambda$RcsParticipant$up5zUlvCkFUru1_1NfgXrzNmBic;
import android.telephony.ims._$$Lambda$RcsParticipant$xir_e_NE3auWDac4dOx89mKtRKU;
import android.telephony.ims.aidl.IRcs;

public class RcsParticipant {
    private final int mId;
    private final RcsControllerCall mRcsControllerCall;

    public RcsParticipant(RcsControllerCall rcsControllerCall, int n) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof RcsParticipant)) {
            return false;
        }
        object = (RcsParticipant)object;
        if (this.mId != ((RcsParticipant)object).mId) {
            bl = false;
        }
        return bl;
    }

    public String getAlias() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsParticipant$MNtRFbM6h_ycH3bPEUZgB5f56zs(this));
    }

    public String getCanonicalAddress() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsParticipant$T35onLZnU_uRTl7zQ7ZWRFtFvx4(this));
    }

    public String getContactId() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsParticipant$up5zUlvCkFUru1_1NfgXrzNmBic(this));
    }

    public int getId() {
        return this.mId;
    }

    public int hashCode() {
        return this.mId;
    }

    public /* synthetic */ String lambda$getAlias$1$RcsParticipant(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getRcsParticipantAlias(this.mId, string2);
    }

    public /* synthetic */ String lambda$getCanonicalAddress$0$RcsParticipant(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getRcsParticipantCanonicalAddress(this.mId, string2);
    }

    public /* synthetic */ String lambda$getContactId$3$RcsParticipant(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getRcsParticipantContactId(this.mId, string2);
    }

    public /* synthetic */ void lambda$setAlias$2$RcsParticipant(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setRcsParticipantAlias(this.mId, string2, string3);
    }

    public /* synthetic */ void lambda$setContactId$4$RcsParticipant(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setRcsParticipantContactId(this.mId, string2, string3);
    }

    public void setAlias(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsParticipant$xir_e_NE3auWDac4dOx89mKtRKU(this, string2));
    }

    public void setContactId(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsParticipant$HgHlMU15W2RReyvhk_UQ_432pfA(this, string2));
    }
}


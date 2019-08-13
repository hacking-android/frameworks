/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims.RcsOutgoingMessageDelivery;
import android.telephony.ims._$$Lambda$RcsOutgoingMessage$uP_7yJmMalJRjXgq_qS_YvAUKuo;
import android.telephony.ims.aidl.IRcs;
import java.util.ArrayList;
import java.util.List;

public class RcsOutgoingMessage
extends RcsMessage {
    RcsOutgoingMessage(RcsControllerCall rcsControllerCall, int n) {
        super(rcsControllerCall, n);
    }

    public List<RcsOutgoingMessageDelivery> getOutgoingDeliveries() throws RcsMessageStoreException {
        ArrayList<RcsOutgoingMessageDelivery> arrayList = new ArrayList<RcsOutgoingMessageDelivery>();
        int[] arrn = (int[])this.mRcsControllerCall.call(new _$$Lambda$RcsOutgoingMessage$uP_7yJmMalJRjXgq_qS_YvAUKuo(this));
        if (arrn != null) {
            for (int n : arrn) {
                arrayList.add(new RcsOutgoingMessageDelivery(this.mRcsControllerCall, n, this.mId));
            }
        }
        return arrayList;
    }

    @Override
    public boolean isIncoming() {
        return false;
    }

    public /* synthetic */ int[] lambda$getOutgoingDeliveries$0$RcsOutgoingMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageRecipients(this.mId, string2);
    }
}


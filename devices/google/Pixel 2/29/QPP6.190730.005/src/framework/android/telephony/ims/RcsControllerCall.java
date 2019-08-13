/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims._$$Lambda$RcsControllerCall$lqKvRobLziMoZre7XkbJkfc5LEM;
import android.telephony.ims.aidl.IRcs;

class RcsControllerCall {
    private final Context mContext;

    RcsControllerCall(Context context) {
        this.mContext = context;
    }

    static /* synthetic */ Object lambda$callWithNoReturn$0(RcsServiceCallWithNoReturn rcsServiceCallWithNoReturn, IRcs iRcs, String string2) throws RemoteException {
        rcsServiceCallWithNoReturn.methodOnIRcs(iRcs, string2);
        return null;
    }

    <R> R call(RcsServiceCall<R> rcsServiceCall) throws RcsMessageStoreException {
        IRcs iRcs = IRcs.Stub.asInterface(ServiceManager.getService("ircs"));
        if (iRcs != null) {
            try {
                rcsServiceCall = rcsServiceCall.methodOnIRcs(iRcs, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                throw new RcsMessageStoreException(remoteException.getMessage());
            }
            return (R)rcsServiceCall;
        }
        throw new RcsMessageStoreException("Could not connect to RCS storage service");
    }

    void callWithNoReturn(RcsServiceCallWithNoReturn rcsServiceCallWithNoReturn) throws RcsMessageStoreException {
        this.call(new _$$Lambda$RcsControllerCall$lqKvRobLziMoZre7XkbJkfc5LEM(rcsServiceCallWithNoReturn));
    }

    static interface RcsServiceCall<R> {
        public R methodOnIRcs(IRcs var1, String var2) throws RemoteException;
    }

    static interface RcsServiceCallWithNoReturn {
        public void methodOnIRcs(IRcs var1, String var2) throws RemoteException;
    }

}


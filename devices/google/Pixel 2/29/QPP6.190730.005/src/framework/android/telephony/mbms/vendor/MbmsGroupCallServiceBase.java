/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.mbms.GroupCallCallback;
import android.telephony.mbms.IGroupCallCallback;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import android.telephony.mbms.MbmsGroupCallSessionCallback;
import android.telephony.mbms.vendor.IMbmsGroupCallService;
import java.util.List;

@SystemApi
public class MbmsGroupCallServiceBase
extends Service {
    private final IBinder mInterface = new IMbmsGroupCallService.Stub(){

        @Override
        public void dispose(int n) throws RemoteException {
            MbmsGroupCallServiceBase.this.dispose(n);
        }

        @Override
        public int initialize(final IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, final int n) throws RemoteException {
            if (iMbmsGroupCallSessionCallback != null) {
                final int n2 = Binder.getCallingUid();
                int n3 = MbmsGroupCallServiceBase.this.initialize(new MbmsGroupCallSessionCallback(){

                    public void onAvailableSaisUpdated(List list, List list2) {
                        try {
                            iMbmsGroupCallSessionCallback.onAvailableSaisUpdated(list, list2);
                        }
                        catch (RemoteException remoteException) {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void onError(int n3, String object) {
                        if (n3 != -1) {
                            try {
                                iMbmsGroupCallSessionCallback.onError(n3, (String)object);
                                return;
                            }
                            catch (RemoteException remoteException) {}
                        } else {
                            object = new IllegalArgumentException("Middleware cannot send an unknown error.");
                            throw object;
                        }
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                    }

                    @Override
                    public void onMiddlewareReady() {
                        try {
                            iMbmsGroupCallSessionCallback.onMiddlewareReady();
                        }
                        catch (RemoteException remoteException) {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }

                    @Override
                    public void onServiceInterfaceAvailable(String string2, int n3) {
                        try {
                            iMbmsGroupCallSessionCallback.onServiceInterfaceAvailable(string2, n3);
                        }
                        catch (RemoteException remoteException) {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }
                }, n);
                if (n3 == 0) {
                    iMbmsGroupCallSessionCallback.asBinder().linkToDeath(new IBinder.DeathRecipient(){

                        @Override
                        public void binderDied() {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }, 0);
                }
                return n3;
            }
            throw new NullPointerException("Callback must not be null");
        }

        @Override
        public int startGroupCall(final int n, long l, List list, List list2, final IGroupCallCallback iGroupCallCallback) throws RemoteException {
            if (iGroupCallCallback != null) {
                final int n2 = Binder.getCallingUid();
                int n3 = MbmsGroupCallServiceBase.this.startGroupCall(n, l, list, list2, new GroupCallCallback(){

                    @Override
                    public void onBroadcastSignalStrengthUpdated(int n3) {
                        try {
                            iGroupCallCallback.onBroadcastSignalStrengthUpdated(n3);
                        }
                        catch (RemoteException remoteException) {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void onError(int n3, String object) {
                        if (n3 != -1) {
                            try {
                                iGroupCallCallback.onError(n3, (String)object);
                                return;
                            }
                            catch (RemoteException remoteException) {}
                        } else {
                            object = new IllegalArgumentException("Middleware cannot send an unknown error.");
                            throw object;
                        }
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                    }

                    @Override
                    public void onGroupCallStateChanged(int n3, int n22) {
                        try {
                            iGroupCallCallback.onGroupCallStateChanged(n3, n22);
                        }
                        catch (RemoteException remoteException) {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }
                });
                if (n3 == 0) {
                    iGroupCallCallback.asBinder().linkToDeath(new IBinder.DeathRecipient(){

                        @Override
                        public void binderDied() {
                            MbmsGroupCallServiceBase.this.onAppCallbackDied(n2, n);
                        }
                    }, 0);
                }
                return n3;
            }
            throw new NullPointerException("Callback must not be null");
        }

        @Override
        public void stopGroupCall(int n, long l) {
            MbmsGroupCallServiceBase.this.stopGroupCall(n, l);
        }

        @Override
        public void updateGroupCall(int n, long l, List list, List list2) {
            MbmsGroupCallServiceBase.this.updateGroupCall(n, l, list, list2);
        }

    };

    public void dispose(int n) throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int initialize(MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback, int n) throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onAppCallbackDied(int n, int n2) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mInterface;
    }

    public int startGroupCall(int n, long l, List<Integer> list, List<Integer> list2, GroupCallCallback groupCallCallback) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void stopGroupCall(int n, long l) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void updateGroupCall(int n, long l, List<Integer> list, List<Integer> list2) {
        throw new UnsupportedOperationException("Not implemented");
    }

}


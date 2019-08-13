/*
 * Decompiled with CFR 0.145.
 */
package android.media.soundtrigger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISoundTriggerDetectionServiceClient
extends IInterface {
    public void onOpFinished(int var1) throws RemoteException;

    public static class Default
    implements ISoundTriggerDetectionServiceClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onOpFinished(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISoundTriggerDetectionServiceClient {
        private static final String DESCRIPTOR = "android.media.soundtrigger.ISoundTriggerDetectionServiceClient";
        static final int TRANSACTION_onOpFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerDetectionServiceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISoundTriggerDetectionServiceClient) {
                return (ISoundTriggerDetectionServiceClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISoundTriggerDetectionServiceClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onOpFinished";
        }

        public static boolean setDefaultImpl(ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) {
            if (Proxy.sDefaultImpl == null && iSoundTriggerDetectionServiceClient != null) {
                Proxy.sDefaultImpl = iSoundTriggerDetectionServiceClient;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onOpFinished(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ISoundTriggerDetectionServiceClient {
            public static ISoundTriggerDetectionServiceClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onOpFinished(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onOpFinished(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}


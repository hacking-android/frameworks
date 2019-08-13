/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.media.tv.TvStreamConfig;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITvInputHardwareCallback
extends IInterface {
    public void onReleased() throws RemoteException;

    public void onStreamConfigChanged(TvStreamConfig[] var1) throws RemoteException;

    public static class Default
    implements ITvInputHardwareCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onReleased() throws RemoteException {
        }

        @Override
        public void onStreamConfigChanged(TvStreamConfig[] arrtvStreamConfig) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputHardwareCallback {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputHardwareCallback";
        static final int TRANSACTION_onReleased = 1;
        static final int TRANSACTION_onStreamConfigChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputHardwareCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputHardwareCallback) {
                return (ITvInputHardwareCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputHardwareCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onStreamConfigChanged";
            }
            return "onReleased";
        }

        public static boolean setDefaultImpl(ITvInputHardwareCallback iTvInputHardwareCallback) {
            if (Proxy.sDefaultImpl == null && iTvInputHardwareCallback != null) {
                Proxy.sDefaultImpl = iTvInputHardwareCallback;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onStreamConfigChanged(parcel.createTypedArray(TvStreamConfig.CREATOR));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onReleased();
            return true;
        }

        private static class Proxy
        implements ITvInputHardwareCallback {
            public static ITvInputHardwareCallback sDefaultImpl;
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
            public void onReleased() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReleased();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStreamConfigChanged(TvStreamConfig[] arrtvStreamConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrtvStreamConfig, 0);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStreamConfigChanged(arrtvStreamConfig);
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


/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.RemoteDisplayState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IRemoteDisplayCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onStateChanged(RemoteDisplayState var1) throws RemoteException;

    public static class Default
    implements IRemoteDisplayCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStateChanged(RemoteDisplayState remoteDisplayState) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRemoteDisplayCallback {
        private static final String DESCRIPTOR = "android.media.IRemoteDisplayCallback";
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteDisplayCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRemoteDisplayCallback) {
                return (IRemoteDisplayCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRemoteDisplayCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStateChanged";
        }

        public static boolean setDefaultImpl(IRemoteDisplayCallback iRemoteDisplayCallback) {
            if (Proxy.sDefaultImpl == null && iRemoteDisplayCallback != null) {
                Proxy.sDefaultImpl = iRemoteDisplayCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? RemoteDisplayState.CREATOR.createFromParcel((Parcel)object) : null;
            this.onStateChanged((RemoteDisplayState)object);
            return true;
        }

        private static class Proxy
        implements IRemoteDisplayCallback {
            public static IRemoteDisplayCallback sDefaultImpl;
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
            public void onStateChanged(RemoteDisplayState remoteDisplayState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteDisplayState != null) {
                        parcel.writeInt(1);
                        remoteDisplayState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(remoteDisplayState);
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


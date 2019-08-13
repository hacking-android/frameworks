/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.Network;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITetheringEventCallback
extends IInterface {
    public void onUpstreamChanged(Network var1) throws RemoteException;

    public static class Default
    implements ITetheringEventCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onUpstreamChanged(Network network) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITetheringEventCallback {
        private static final String DESCRIPTOR = "android.net.ITetheringEventCallback";
        static final int TRANSACTION_onUpstreamChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITetheringEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITetheringEventCallback) {
                return (ITetheringEventCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITetheringEventCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onUpstreamChanged";
        }

        public static boolean setDefaultImpl(ITetheringEventCallback iTetheringEventCallback) {
            if (Proxy.sDefaultImpl == null && iTetheringEventCallback != null) {
                Proxy.sDefaultImpl = iTetheringEventCallback;
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
            object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
            this.onUpstreamChanged((Network)object);
            return true;
        }

        private static class Proxy
        implements ITetheringEventCallback {
            public static ITetheringEventCallback sDefaultImpl;
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
            public void onUpstreamChanged(Network network) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUpstreamChanged(network);
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


/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.NanoAppState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IContextHubTransactionCallback
extends IInterface {
    public void onQueryResponse(int var1, List<NanoAppState> var2) throws RemoteException;

    public void onTransactionComplete(int var1) throws RemoteException;

    public static class Default
    implements IContextHubTransactionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onQueryResponse(int n, List<NanoAppState> list) throws RemoteException {
        }

        @Override
        public void onTransactionComplete(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContextHubTransactionCallback {
        private static final String DESCRIPTOR = "android.hardware.location.IContextHubTransactionCallback";
        static final int TRANSACTION_onQueryResponse = 1;
        static final int TRANSACTION_onTransactionComplete = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubTransactionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContextHubTransactionCallback) {
                return (IContextHubTransactionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContextHubTransactionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onTransactionComplete";
            }
            return "onQueryResponse";
        }

        public static boolean setDefaultImpl(IContextHubTransactionCallback iContextHubTransactionCallback) {
            if (Proxy.sDefaultImpl == null && iContextHubTransactionCallback != null) {
                Proxy.sDefaultImpl = iContextHubTransactionCallback;
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
                this.onTransactionComplete(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onQueryResponse(parcel.readInt(), parcel.createTypedArrayList(NanoAppState.CREATOR));
            return true;
        }

        private static class Proxy
        implements IContextHubTransactionCallback {
            public static IContextHubTransactionCallback sDefaultImpl;
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
            public void onQueryResponse(int n, List<NanoAppState> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onQueryResponse(n, list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTransactionComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTransactionComplete(n);
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


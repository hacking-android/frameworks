/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.net.wifi.rtt.RangingResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IRttCallback
extends IInterface {
    public void onRangingFailure(int var1) throws RemoteException;

    public void onRangingResults(List<RangingResult> var1) throws RemoteException;

    public static class Default
    implements IRttCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onRangingFailure(int n) throws RemoteException {
        }

        @Override
        public void onRangingResults(List<RangingResult> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRttCallback {
        private static final String DESCRIPTOR = "android.net.wifi.rtt.IRttCallback";
        static final int TRANSACTION_onRangingFailure = 1;
        static final int TRANSACTION_onRangingResults = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRttCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRttCallback) {
                return (IRttCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRttCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onRangingResults";
            }
            return "onRangingFailure";
        }

        public static boolean setDefaultImpl(IRttCallback iRttCallback) {
            if (Proxy.sDefaultImpl == null && iRttCallback != null) {
                Proxy.sDefaultImpl = iRttCallback;
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
                this.onRangingResults(parcel.createTypedArrayList(RangingResult.CREATOR));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onRangingFailure(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IRttCallback {
            public static IRttCallback sDefaultImpl;
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
            public void onRangingFailure(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRangingFailure(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRangingResults(List<RangingResult> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRangingResults(list);
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


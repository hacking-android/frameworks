/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.telephony.CellInfo;
import java.util.ArrayList;
import java.util.List;

public interface ICellInfoCallback
extends IInterface {
    public void onCellInfo(List<CellInfo> var1) throws RemoteException;

    public void onError(int var1, ParcelableException var2) throws RemoteException;

    public static class Default
    implements ICellInfoCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCellInfo(List<CellInfo> list) throws RemoteException {
        }

        @Override
        public void onError(int n, ParcelableException parcelableException) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICellInfoCallback {
        private static final String DESCRIPTOR = "android.telephony.ICellInfoCallback";
        static final int TRANSACTION_onCellInfo = 1;
        static final int TRANSACTION_onError = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICellInfoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICellInfoCallback) {
                return (ICellInfoCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICellInfoCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onError";
            }
            return "onCellInfo";
        }

        public static boolean setDefaultImpl(ICellInfoCallback iCellInfoCallback) {
            if (Proxy.sDefaultImpl == null && iCellInfoCallback != null) {
                Proxy.sDefaultImpl = iCellInfoCallback;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? ParcelableException.CREATOR.createFromParcel((Parcel)object) : null;
                this.onError(n, (ParcelableException)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onCellInfo(((Parcel)object).createTypedArrayList(CellInfo.CREATOR));
            return true;
        }

        private static class Proxy
        implements ICellInfoCallback {
            public static ICellInfoCallback sDefaultImpl;
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
            public void onCellInfo(List<CellInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCellInfo(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n, ParcelableException parcelableException) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (parcelableException != null) {
                        parcel.writeInt(1);
                        parcelableException.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, parcelableException);
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


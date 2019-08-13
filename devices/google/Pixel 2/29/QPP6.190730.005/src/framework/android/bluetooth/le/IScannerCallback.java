/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.le.ScanResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IScannerCallback
extends IInterface {
    public void onBatchScanResults(List<ScanResult> var1) throws RemoteException;

    public void onFoundOrLost(boolean var1, ScanResult var2) throws RemoteException;

    public void onScanManagerErrorCallback(int var1) throws RemoteException;

    public void onScanResult(ScanResult var1) throws RemoteException;

    public void onScannerRegistered(int var1, int var2) throws RemoteException;

    public static class Default
    implements IScannerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onBatchScanResults(List<ScanResult> list) throws RemoteException {
        }

        @Override
        public void onFoundOrLost(boolean bl, ScanResult scanResult) throws RemoteException {
        }

        @Override
        public void onScanManagerErrorCallback(int n) throws RemoteException {
        }

        @Override
        public void onScanResult(ScanResult scanResult) throws RemoteException {
        }

        @Override
        public void onScannerRegistered(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IScannerCallback {
        private static final String DESCRIPTOR = "android.bluetooth.le.IScannerCallback";
        static final int TRANSACTION_onBatchScanResults = 3;
        static final int TRANSACTION_onFoundOrLost = 4;
        static final int TRANSACTION_onScanManagerErrorCallback = 5;
        static final int TRANSACTION_onScanResult = 2;
        static final int TRANSACTION_onScannerRegistered = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IScannerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IScannerCallback) {
                return (IScannerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IScannerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onScanManagerErrorCallback";
                        }
                        return "onFoundOrLost";
                    }
                    return "onBatchScanResults";
                }
                return "onScanResult";
            }
            return "onScannerRegistered";
        }

        public static boolean setDefaultImpl(IScannerCallback iScannerCallback) {
            if (Proxy.sDefaultImpl == null && iScannerCallback != null) {
                Proxy.sDefaultImpl = iScannerCallback;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            this.onScanManagerErrorCallback(((Parcel)object).readInt());
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? ScanResult.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onFoundOrLost(bl, (ScanResult)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onBatchScanResults(((Parcel)object).createTypedArrayList(ScanResult.CREATOR));
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ScanResult.CREATOR.createFromParcel((Parcel)object) : null;
                this.onScanResult((ScanResult)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onScannerRegistered(((Parcel)object).readInt(), ((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements IScannerCallback {
            public static IScannerCallback sDefaultImpl;
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
            public void onBatchScanResults(List<ScanResult> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBatchScanResults(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFoundOrLost(boolean bl, ScanResult scanResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (scanResult != null) {
                        parcel.writeInt(1);
                        scanResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFoundOrLost(bl, scanResult);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onScanManagerErrorCallback(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScanManagerErrorCallback(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onScanResult(ScanResult scanResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scanResult != null) {
                        parcel.writeInt(1);
                        scanResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScanResult(scanResult);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onScannerRegistered(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScannerRegistered(n, n2);
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


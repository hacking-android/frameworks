/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PrintJobInfo;
import java.util.ArrayList;
import java.util.List;

public interface IPrintSpoolerCallbacks
extends IInterface {
    public void customPrinterIconCacheCleared(int var1) throws RemoteException;

    public void onCancelPrintJobResult(boolean var1, int var2) throws RemoteException;

    public void onCustomPrinterIconCached(int var1) throws RemoteException;

    public void onGetCustomPrinterIconResult(Icon var1, int var2) throws RemoteException;

    public void onGetPrintJobInfoResult(PrintJobInfo var1, int var2) throws RemoteException;

    public void onGetPrintJobInfosResult(List<PrintJobInfo> var1, int var2) throws RemoteException;

    public void onSetPrintJobStateResult(boolean var1, int var2) throws RemoteException;

    public void onSetPrintJobTagResult(boolean var1, int var2) throws RemoteException;

    public static class Default
    implements IPrintSpoolerCallbacks {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void customPrinterIconCacheCleared(int n) throws RemoteException {
        }

        @Override
        public void onCancelPrintJobResult(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void onCustomPrinterIconCached(int n) throws RemoteException {
        }

        @Override
        public void onGetCustomPrinterIconResult(Icon icon, int n) throws RemoteException {
        }

        @Override
        public void onGetPrintJobInfoResult(PrintJobInfo printJobInfo, int n) throws RemoteException {
        }

        @Override
        public void onGetPrintJobInfosResult(List<PrintJobInfo> list, int n) throws RemoteException {
        }

        @Override
        public void onSetPrintJobStateResult(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void onSetPrintJobTagResult(boolean bl, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintSpoolerCallbacks {
        private static final String DESCRIPTOR = "android.print.IPrintSpoolerCallbacks";
        static final int TRANSACTION_customPrinterIconCacheCleared = 8;
        static final int TRANSACTION_onCancelPrintJobResult = 2;
        static final int TRANSACTION_onCustomPrinterIconCached = 7;
        static final int TRANSACTION_onGetCustomPrinterIconResult = 6;
        static final int TRANSACTION_onGetPrintJobInfoResult = 5;
        static final int TRANSACTION_onGetPrintJobInfosResult = 1;
        static final int TRANSACTION_onSetPrintJobStateResult = 3;
        static final int TRANSACTION_onSetPrintJobTagResult = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpoolerCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintSpoolerCallbacks) {
                return (IPrintSpoolerCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintSpoolerCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "customPrinterIconCacheCleared";
                }
                case 7: {
                    return "onCustomPrinterIconCached";
                }
                case 6: {
                    return "onGetCustomPrinterIconResult";
                }
                case 5: {
                    return "onGetPrintJobInfoResult";
                }
                case 4: {
                    return "onSetPrintJobTagResult";
                }
                case 3: {
                    return "onSetPrintJobStateResult";
                }
                case 2: {
                    return "onCancelPrintJobResult";
                }
                case 1: 
            }
            return "onGetPrintJobInfosResult";
        }

        public static boolean setDefaultImpl(IPrintSpoolerCallbacks iPrintSpoolerCallbacks) {
            if (Proxy.sDefaultImpl == null && iPrintSpoolerCallbacks != null) {
                Proxy.sDefaultImpl = iPrintSpoolerCallbacks;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.customPrinterIconCacheCleared(parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCustomPrinterIconCached(parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? Icon.CREATOR.createFromParcel(parcel) : null;
                        this.onGetCustomPrinterIconResult((Icon)object, parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel(parcel) : null;
                        this.onGetPrintJobInfoResult((PrintJobInfo)object, parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bl3 = true;
                        }
                        this.onSetPrintJobTagResult(bl3, parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        bl3 = bl;
                        if (parcel.readInt() != 0) {
                            bl3 = true;
                        }
                        this.onSetPrintJobStateResult(bl3, parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        bl3 = bl2;
                        if (parcel.readInt() != 0) {
                            bl3 = true;
                        }
                        this.onCancelPrintJobResult(bl3, parcel.readInt());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onGetPrintJobInfosResult(parcel.createTypedArrayList(PrintJobInfo.CREATOR), parcel.readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPrintSpoolerCallbacks {
            public static IPrintSpoolerCallbacks sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void customPrinterIconCacheCleared(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().customPrinterIconCacheCleared(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onCancelPrintJobResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCancelPrintJobResult(bl, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCustomPrinterIconCached(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCustomPrinterIconCached(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGetCustomPrinterIconResult(Icon icon, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (icon != null) {
                        parcel.writeInt(1);
                        icon.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetCustomPrinterIconResult(icon, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGetPrintJobInfoResult(PrintJobInfo printJobInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobInfo != null) {
                        parcel.writeInt(1);
                        printJobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetPrintJobInfoResult(printJobInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGetPrintJobInfosResult(List<PrintJobInfo> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetPrintJobInfosResult(list, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSetPrintJobStateResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetPrintJobStateResult(bl, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSetPrintJobTagResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetPrintJobTagResult(bl, n);
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


/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PrintJobId;

public interface IPrintJobStateChangeListener
extends IInterface {
    public void onPrintJobStateChanged(PrintJobId var1) throws RemoteException;

    public static class Default
    implements IPrintJobStateChangeListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPrintJobStateChanged(PrintJobId printJobId) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintJobStateChangeListener {
        private static final String DESCRIPTOR = "android.print.IPrintJobStateChangeListener";
        static final int TRANSACTION_onPrintJobStateChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintJobStateChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintJobStateChangeListener) {
                return (IPrintJobStateChangeListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintJobStateChangeListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onPrintJobStateChanged";
        }

        public static boolean setDefaultImpl(IPrintJobStateChangeListener iPrintJobStateChangeListener) {
            if (Proxy.sDefaultImpl == null && iPrintJobStateChangeListener != null) {
                Proxy.sDefaultImpl = iPrintJobStateChangeListener;
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
            object = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
            this.onPrintJobStateChanged((PrintJobId)object);
            return true;
        }

        private static class Proxy
        implements IPrintJobStateChangeListener {
            public static IPrintJobStateChangeListener sDefaultImpl;
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
            public void onPrintJobStateChanged(PrintJobId printJobId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintJobStateChanged(printJobId);
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


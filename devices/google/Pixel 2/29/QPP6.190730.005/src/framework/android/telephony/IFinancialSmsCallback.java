/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.database.CursorWindow;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IFinancialSmsCallback
extends IInterface {
    public void onGetSmsMessagesForFinancialApp(CursorWindow var1) throws RemoteException;

    public static class Default
    implements IFinancialSmsCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onGetSmsMessagesForFinancialApp(CursorWindow cursorWindow) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFinancialSmsCallback {
        private static final String DESCRIPTOR = "android.telephony.IFinancialSmsCallback";
        static final int TRANSACTION_onGetSmsMessagesForFinancialApp = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFinancialSmsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFinancialSmsCallback) {
                return (IFinancialSmsCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFinancialSmsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onGetSmsMessagesForFinancialApp";
        }

        public static boolean setDefaultImpl(IFinancialSmsCallback iFinancialSmsCallback) {
            if (Proxy.sDefaultImpl == null && iFinancialSmsCallback != null) {
                Proxy.sDefaultImpl = iFinancialSmsCallback;
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
            object = ((Parcel)object).readInt() != 0 ? CursorWindow.CREATOR.createFromParcel((Parcel)object) : null;
            this.onGetSmsMessagesForFinancialApp((CursorWindow)object);
            return true;
        }

        private static class Proxy
        implements IFinancialSmsCallback {
            public static IFinancialSmsCallback sDefaultImpl;
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
            public void onGetSmsMessagesForFinancialApp(CursorWindow cursorWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cursorWindow != null) {
                        parcel.writeInt(1);
                        cursorWindow.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSmsMessagesForFinancialApp(cursorWindow);
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


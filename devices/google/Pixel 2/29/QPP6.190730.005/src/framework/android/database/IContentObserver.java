/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IContentObserver
extends IInterface {
    public void onChange(boolean var1, Uri var2, int var3) throws RemoteException;

    public static class Default
    implements IContentObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onChange(boolean bl, Uri uri, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentObserver {
        private static final String DESCRIPTOR = "android.database.IContentObserver";
        static final int TRANSACTION_onChange = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentObserver) {
                return (IContentObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onChange";
        }

        public static boolean setDefaultImpl(IContentObserver iContentObserver) {
            if (Proxy.sDefaultImpl == null && iContentObserver != null) {
                Proxy.sDefaultImpl = iContentObserver;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean bl = parcel.readInt() != 0;
            object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
            this.onChange(bl, (Uri)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IContentObserver {
            public static IContentObserver sDefaultImpl;
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
            public void onChange(boolean bl, Uri uri, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onChange(bl, uri, n);
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


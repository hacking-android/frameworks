/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.app.contentsuggestions.ContentClassification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IClassificationsCallback
extends IInterface {
    public void onContentClassificationsAvailable(int var1, List<ContentClassification> var2) throws RemoteException;

    public static class Default
    implements IClassificationsCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onContentClassificationsAvailable(int n, List<ContentClassification> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IClassificationsCallback {
        private static final String DESCRIPTOR = "android.app.contentsuggestions.IClassificationsCallback";
        static final int TRANSACTION_onContentClassificationsAvailable = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IClassificationsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IClassificationsCallback) {
                return (IClassificationsCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IClassificationsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onContentClassificationsAvailable";
        }

        public static boolean setDefaultImpl(IClassificationsCallback iClassificationsCallback) {
            if (Proxy.sDefaultImpl == null && iClassificationsCallback != null) {
                Proxy.sDefaultImpl = iClassificationsCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onContentClassificationsAvailable(parcel.readInt(), parcel.createTypedArrayList(ContentClassification.CREATOR));
            return true;
        }

        private static class Proxy
        implements IClassificationsCallback {
            public static IClassificationsCallback sDefaultImpl;
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
            public void onContentClassificationsAvailable(int n, List<ContentClassification> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onContentClassificationsAvailable(n, list);
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


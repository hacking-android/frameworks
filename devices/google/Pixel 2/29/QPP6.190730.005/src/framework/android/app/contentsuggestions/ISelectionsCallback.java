/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.app.contentsuggestions.ContentSelection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ISelectionsCallback
extends IInterface {
    public void onContentSelectionsAvailable(int var1, List<ContentSelection> var2) throws RemoteException;

    public static class Default
    implements ISelectionsCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onContentSelectionsAvailable(int n, List<ContentSelection> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISelectionsCallback {
        private static final String DESCRIPTOR = "android.app.contentsuggestions.ISelectionsCallback";
        static final int TRANSACTION_onContentSelectionsAvailable = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISelectionsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISelectionsCallback) {
                return (ISelectionsCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISelectionsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onContentSelectionsAvailable";
        }

        public static boolean setDefaultImpl(ISelectionsCallback iSelectionsCallback) {
            if (Proxy.sDefaultImpl == null && iSelectionsCallback != null) {
                Proxy.sDefaultImpl = iSelectionsCallback;
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
            this.onContentSelectionsAvailable(parcel.readInt(), parcel.createTypedArrayList(ContentSelection.CREATOR));
            return true;
        }

        private static class Proxy
        implements ISelectionsCallback {
            public static ISelectionsCallback sDefaultImpl;
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
            public void onContentSelectionsAvailable(int n, List<ContentSelection> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onContentSelectionsAvailable(n, list);
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


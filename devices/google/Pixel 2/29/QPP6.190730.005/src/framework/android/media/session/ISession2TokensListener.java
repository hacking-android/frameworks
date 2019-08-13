/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.Session2Token
 */
package android.media.session;

import android.media.Session2Token;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ISession2TokensListener
extends IInterface {
    public void onSession2TokensChanged(List<Session2Token> var1) throws RemoteException;

    public static class Default
    implements ISession2TokensListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSession2TokensChanged(List<Session2Token> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISession2TokensListener {
        private static final String DESCRIPTOR = "android.media.session.ISession2TokensListener";
        static final int TRANSACTION_onSession2TokensChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISession2TokensListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISession2TokensListener) {
                return (ISession2TokensListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISession2TokensListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSession2TokensChanged";
        }

        public static boolean setDefaultImpl(ISession2TokensListener iSession2TokensListener) {
            if (Proxy.sDefaultImpl == null && iSession2TokensListener != null) {
                Proxy.sDefaultImpl = iSession2TokensListener;
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
            this.onSession2TokensChanged(parcel.createTypedArrayList(Session2Token.CREATOR));
            return true;
        }

        private static class Proxy
        implements ISession2TokensListener {
            public static ISession2TokensListener sDefaultImpl;
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
            public void onSession2TokensChanged(List<Session2Token> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSession2TokensChanged(list);
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


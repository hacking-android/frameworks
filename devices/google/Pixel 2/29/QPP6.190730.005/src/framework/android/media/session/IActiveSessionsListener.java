/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IActiveSessionsListener
extends IInterface {
    public void onActiveSessionsChanged(List<MediaSession.Token> var1) throws RemoteException;

    public static class Default
    implements IActiveSessionsListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActiveSessionsChanged(List<MediaSession.Token> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IActiveSessionsListener {
        private static final String DESCRIPTOR = "android.media.session.IActiveSessionsListener";
        static final int TRANSACTION_onActiveSessionsChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IActiveSessionsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IActiveSessionsListener) {
                return (IActiveSessionsListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IActiveSessionsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onActiveSessionsChanged";
        }

        public static boolean setDefaultImpl(IActiveSessionsListener iActiveSessionsListener) {
            if (Proxy.sDefaultImpl == null && iActiveSessionsListener != null) {
                Proxy.sDefaultImpl = iActiveSessionsListener;
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
            this.onActiveSessionsChanged(parcel.createTypedArrayList(MediaSession.Token.CREATOR));
            return true;
        }

        private static class Proxy
        implements IActiveSessionsListener {
            public static IActiveSessionsListener sDefaultImpl;
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
            public void onActiveSessionsChanged(List<MediaSession.Token> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActiveSessionsChanged(list);
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


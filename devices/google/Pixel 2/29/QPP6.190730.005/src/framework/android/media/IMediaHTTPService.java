/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.IMediaHTTPConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaHTTPService
extends IInterface {
    public IMediaHTTPConnection makeHTTPConnection() throws RemoteException;

    public static class Default
    implements IMediaHTTPService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public IMediaHTTPConnection makeHTTPConnection() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaHTTPService {
        private static final String DESCRIPTOR = "android.media.IMediaHTTPService";
        static final int TRANSACTION_makeHTTPConnection = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaHTTPService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaHTTPService) {
                return (IMediaHTTPService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaHTTPService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "makeHTTPConnection";
        }

        public static boolean setDefaultImpl(IMediaHTTPService iMediaHTTPService) {
            if (Proxy.sDefaultImpl == null && iMediaHTTPService != null) {
                Proxy.sDefaultImpl = iMediaHTTPService;
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
            object = this.makeHTTPConnection();
            parcel.writeNoException();
            object = object != null ? object.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class Proxy
        implements IMediaHTTPService {
            public static IMediaHTTPService sDefaultImpl;
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
            public IMediaHTTPConnection makeHTTPConnection() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IMediaHTTPConnection iMediaHTTPConnection = Stub.getDefaultImpl().makeHTTPConnection();
                        return iMediaHTTPConnection;
                    }
                    parcel2.readException();
                    IMediaHTTPConnection iMediaHTTPConnection = IMediaHTTPConnection.Stub.asInterface(parcel2.readStrongBinder());
                    return iMediaHTTPConnection;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.NanoAppMessage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IContextHubClient
extends IInterface {
    public void close() throws RemoteException;

    public int sendMessageToNanoApp(NanoAppMessage var1) throws RemoteException;

    public static class Default
    implements IContextHubClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close() throws RemoteException {
        }

        @Override
        public int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContextHubClient {
        private static final String DESCRIPTOR = "android.hardware.location.IContextHubClient";
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_sendMessageToNanoApp = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContextHubClient) {
                return (IContextHubClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContextHubClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "close";
            }
            return "sendMessageToNanoApp";
        }

        public static boolean setDefaultImpl(IContextHubClient iContextHubClient) {
            if (Proxy.sDefaultImpl == null && iContextHubClient != null) {
                Proxy.sDefaultImpl = iContextHubClient;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.close();
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? NanoAppMessage.CREATOR.createFromParcel((Parcel)object) : null;
            n = this.sendMessageToNanoApp((NanoAppMessage)object);
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IContextHubClient {
            public static IContextHubClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void close() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (nanoAppMessage != null) {
                        parcel.writeInt(1);
                        nanoAppMessage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().sendMessageToNanoApp(nanoAppMessage);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}


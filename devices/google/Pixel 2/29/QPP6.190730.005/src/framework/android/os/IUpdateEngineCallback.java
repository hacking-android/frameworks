/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUpdateEngineCallback
extends IInterface {
    public void onPayloadApplicationComplete(int var1) throws RemoteException;

    public void onStatusUpdate(int var1, float var2) throws RemoteException;

    public static class Default
    implements IUpdateEngineCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPayloadApplicationComplete(int n) throws RemoteException {
        }

        @Override
        public void onStatusUpdate(int n, float f) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUpdateEngineCallback {
        private static final String DESCRIPTOR = "android.os.IUpdateEngineCallback";
        static final int TRANSACTION_onPayloadApplicationComplete = 2;
        static final int TRANSACTION_onStatusUpdate = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateEngineCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUpdateEngineCallback) {
                return (IUpdateEngineCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUpdateEngineCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onPayloadApplicationComplete";
            }
            return "onStatusUpdate";
        }

        public static boolean setDefaultImpl(IUpdateEngineCallback iUpdateEngineCallback) {
            if (Proxy.sDefaultImpl == null && iUpdateEngineCallback != null) {
                Proxy.sDefaultImpl = iUpdateEngineCallback;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onPayloadApplicationComplete(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onStatusUpdate(parcel.readInt(), parcel.readFloat());
            return true;
        }

        private static class Proxy
        implements IUpdateEngineCallback {
            public static IUpdateEngineCallback sDefaultImpl;
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
            public void onPayloadApplicationComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPayloadApplicationComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStatusUpdate(int n, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusUpdate(n, f);
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


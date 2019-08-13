/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.media.tv.ITvRemoteServiceInput;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITvRemoteProvider
extends IInterface {
    public void onInputBridgeConnected(IBinder var1) throws RemoteException;

    public void setRemoteServiceInputSink(ITvRemoteServiceInput var1) throws RemoteException;

    public static class Default
    implements ITvRemoteProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onInputBridgeConnected(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvRemoteProvider {
        private static final String DESCRIPTOR = "android.media.tv.ITvRemoteProvider";
        static final int TRANSACTION_onInputBridgeConnected = 2;
        static final int TRANSACTION_setRemoteServiceInputSink = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvRemoteProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvRemoteProvider) {
                return (ITvRemoteProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvRemoteProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onInputBridgeConnected";
            }
            return "setRemoteServiceInputSink";
        }

        public static boolean setDefaultImpl(ITvRemoteProvider iTvRemoteProvider) {
            if (Proxy.sDefaultImpl == null && iTvRemoteProvider != null) {
                Proxy.sDefaultImpl = iTvRemoteProvider;
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
                this.onInputBridgeConnected(parcel.readStrongBinder());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.setRemoteServiceInputSink(ITvRemoteServiceInput.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ITvRemoteProvider {
            public static ITvRemoteProvider sDefaultImpl;
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
            public void onInputBridgeConnected(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputBridgeConnected(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTvRemoteServiceInput != null ? iTvRemoteServiceInput.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setRemoteServiceInputSink(iTvRemoteServiceInput);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}


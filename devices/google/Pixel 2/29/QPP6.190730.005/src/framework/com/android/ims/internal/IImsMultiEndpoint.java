/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.IImsExternalCallStateListener;

public interface IImsMultiEndpoint
extends IInterface {
    public void requestImsExternalCallStateInfo() throws RemoteException;

    public void setListener(IImsExternalCallStateListener var1) throws RemoteException;

    public static class Default
    implements IImsMultiEndpoint {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void requestImsExternalCallStateInfo() throws RemoteException {
        }

        @Override
        public void setListener(IImsExternalCallStateListener iImsExternalCallStateListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsMultiEndpoint {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsMultiEndpoint";
        static final int TRANSACTION_requestImsExternalCallStateInfo = 2;
        static final int TRANSACTION_setListener = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsMultiEndpoint asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsMultiEndpoint) {
                return (IImsMultiEndpoint)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsMultiEndpoint getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "requestImsExternalCallStateInfo";
            }
            return "setListener";
        }

        public static boolean setDefaultImpl(IImsMultiEndpoint iImsMultiEndpoint) {
            if (Proxy.sDefaultImpl == null && iImsMultiEndpoint != null) {
                Proxy.sDefaultImpl = iImsMultiEndpoint;
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
                this.requestImsExternalCallStateInfo();
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.setListener(IImsExternalCallStateListener.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IImsMultiEndpoint {
            public static IImsMultiEndpoint sDefaultImpl;
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
            public void requestImsExternalCallStateInfo() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestImsExternalCallStateInfo();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setListener(IImsExternalCallStateListener iImsExternalCallStateListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsExternalCallStateListener != null ? iImsExternalCallStateListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setListener(iImsExternalCallStateListener);
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
        }

    }

}


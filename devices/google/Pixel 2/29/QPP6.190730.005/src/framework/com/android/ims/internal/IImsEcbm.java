/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.IImsEcbmListener;

public interface IImsEcbm
extends IInterface {
    public void exitEmergencyCallbackMode() throws RemoteException;

    public void setListener(IImsEcbmListener var1) throws RemoteException;

    public static class Default
    implements IImsEcbm {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void exitEmergencyCallbackMode() throws RemoteException {
        }

        @Override
        public void setListener(IImsEcbmListener iImsEcbmListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsEcbm {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsEcbm";
        static final int TRANSACTION_exitEmergencyCallbackMode = 2;
        static final int TRANSACTION_setListener = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsEcbm asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsEcbm) {
                return (IImsEcbm)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsEcbm getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "exitEmergencyCallbackMode";
            }
            return "setListener";
        }

        public static boolean setDefaultImpl(IImsEcbm iImsEcbm) {
            if (Proxy.sDefaultImpl == null && iImsEcbm != null) {
                Proxy.sDefaultImpl = iImsEcbm;
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
                this.exitEmergencyCallbackMode();
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.setListener(IImsEcbmListener.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IImsEcbm {
            public static IImsEcbm sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void exitEmergencyCallbackMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().exitEmergencyCallbackMode();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setListener(IImsEcbmListener iImsEcbmListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsEcbmListener != null ? iImsEcbmListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setListener(iImsEcbmListener);
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


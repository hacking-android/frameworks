/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVoiceInteractionSessionShowCallback
extends IInterface {
    public void onFailed() throws RemoteException;

    public void onShown() throws RemoteException;

    public static class Default
    implements IVoiceInteractionSessionShowCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFailed() throws RemoteException {
        }

        @Override
        public void onShown() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractionSessionShowCallback {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSessionShowCallback";
        static final int TRANSACTION_onFailed = 1;
        static final int TRANSACTION_onShown = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSessionShowCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractionSessionShowCallback) {
                return (IVoiceInteractionSessionShowCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractionSessionShowCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onShown";
            }
            return "onFailed";
        }

        public static boolean setDefaultImpl(IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractionSessionShowCallback != null) {
                Proxy.sDefaultImpl = iVoiceInteractionSessionShowCallback;
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
                this.onShown();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onFailed();
            return true;
        }

        private static class Proxy
        implements IVoiceInteractionSessionShowCallback {
            public static IVoiceInteractionSessionShowCallback sDefaultImpl;
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
            public void onFailed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onShown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShown();
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


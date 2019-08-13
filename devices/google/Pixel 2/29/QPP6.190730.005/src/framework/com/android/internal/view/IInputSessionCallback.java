/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.view.IInputMethodSession;

public interface IInputSessionCallback
extends IInterface {
    public void sessionCreated(IInputMethodSession var1) throws RemoteException;

    public static class Default
    implements IInputSessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void sessionCreated(IInputMethodSession iInputMethodSession) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputSessionCallback {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputSessionCallback";
        static final int TRANSACTION_sessionCreated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputSessionCallback) {
                return (IInputSessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputSessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "sessionCreated";
        }

        public static boolean setDefaultImpl(IInputSessionCallback iInputSessionCallback) {
            if (Proxy.sDefaultImpl == null && iInputSessionCallback != null) {
                Proxy.sDefaultImpl = iInputSessionCallback;
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
            this.sessionCreated(IInputMethodSession.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IInputSessionCallback {
            public static IInputSessionCallback sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void sessionCreated(IInputMethodSession iInputMethodSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodSession != null ? iInputMethodSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().sessionCreated(iInputMethodSession);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}


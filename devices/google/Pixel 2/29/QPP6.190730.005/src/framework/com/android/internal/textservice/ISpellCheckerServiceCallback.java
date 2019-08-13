/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.textservice.ISpellCheckerSession;

public interface ISpellCheckerServiceCallback
extends IInterface {
    public void onSessionCreated(ISpellCheckerSession var1) throws RemoteException;

    public static class Default
    implements ISpellCheckerServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISpellCheckerServiceCallback {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerServiceCallback";
        static final int TRANSACTION_onSessionCreated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISpellCheckerServiceCallback) {
                return (ISpellCheckerServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISpellCheckerServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSessionCreated";
        }

        public static boolean setDefaultImpl(ISpellCheckerServiceCallback iSpellCheckerServiceCallback) {
            if (Proxy.sDefaultImpl == null && iSpellCheckerServiceCallback != null) {
                Proxy.sDefaultImpl = iSpellCheckerServiceCallback;
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
            this.onSessionCreated(ISpellCheckerSession.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ISpellCheckerServiceCallback {
            public static ISpellCheckerServiceCallback sDefaultImpl;
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
            public void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSpellCheckerSession != null ? iSpellCheckerSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSessionCreated(iSpellCheckerSession);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

